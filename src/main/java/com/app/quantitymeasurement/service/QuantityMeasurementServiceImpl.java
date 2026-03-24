package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.Quantity;
import com.app.quantitymeasurement.util.MeasurementTypeResolver;
import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);

    private final QuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    @Override
    public QuantityMeasurementDTO compare(QuantityDTO q1, QuantityDTO q2) {
        return executeBinaryOperation(
                OperationType.COMPARE,
                q1,
                q2,
                new TypedQuantityOperation() {
                    @Override
                    public <U extends Enum<U> & IMeasurable> String apply(Quantity<U> left, Quantity<U> right, Class<U> unitClass) {
                        return String.valueOf(left.equals(right));
                    }
                }
        );
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityDTO quantity, String targetUnit) {
        OperationType operationType = OperationType.CONVERT;
        QuantityDTO targetQuantity = new QuantityDTO(0.0, targetUnit, quantity.getMeasurementType(), operationType);

        try {
            String resolvedTargetUnit = targetUnit == null || targetUnit.isBlank() ? quantity.getUnit() : targetUnit;
            String result = executeByMeasurementType(
                    quantity,
                    new QuantityDTO(0.0, resolvedTargetUnit, quantity.getMeasurementType(), operationType),
                    new TypedQuantityOperation() {
                        @Override
                        public <U extends Enum<U> & IMeasurable> String apply(Quantity<U> left, Quantity<U> right, Class<U> unitClass) {
                            return formatQuantity(
                                    left.convertTo(MeasurementTypeResolver.resolveUnit(quantity.getMeasurementType(), resolvedTargetUnit, unitClass))
                            );
                        }
                    }
            );

            return saveSuccess(QuantityMeasurementDTO.success(
                    operationType,
                    quantity,
                    new QuantityDTO(0.0, resolvedTargetUnit, quantity.getMeasurementType(), operationType),
                    result
            ));
        } catch (RuntimeException exception) {
            return handleFailure(operationType, quantity, targetQuantity, exception);
        }
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO q1, QuantityDTO q2) {
        return executeBinaryOperation(
                OperationType.ADD,
                q1,
                q2,
                new TypedQuantityOperation() {
                    @Override
                    public <U extends Enum<U> & IMeasurable> String apply(Quantity<U> left, Quantity<U> right, Class<U> unitClass) {
                        return formatQuantity(left.add(right, left.getUnit()));
                    }
                }
        );
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO q1, QuantityDTO q2) {
        return executeBinaryOperation(
                OperationType.SUBTRACT,
                q1,
                q2,
                new TypedQuantityOperation() {
                    @Override
                    public <U extends Enum<U> & IMeasurable> String apply(Quantity<U> left, Quantity<U> right, Class<U> unitClass) {
                        return formatQuantity(left.subtract(right, left.getUnit()));
                    }
                }
        );
    }

    @Override
    public QuantityMeasurementDTO divide(QuantityDTO q1, QuantityDTO q2) {
        return executeBinaryOperation(
                OperationType.DIVIDE,
                q1,
                q2,
                new TypedQuantityOperation() {
                    @Override
                    public <U extends Enum<U> & IMeasurable> String apply(Quantity<U> left, Quantity<U> right, Class<U> unitClass) {
                        return formatNumber(left.divide(right));
                    }
                }
        );
    }

    @Override
    public List<QuantityMeasurementDTO> getHistoryByOperation(OperationType operationType) {
        return QuantityMeasurementDTO.fromEntityList(repository.findByOperation(operationType));
    }

    @Override
    public List<QuantityMeasurementDTO> getHistoryByMeasurementType(String measurementType) {
        return QuantityMeasurementDTO.fromEntityList(
                repository.findByThisMeasurementType(MeasurementTypeResolver.normalizeMeasurementType(measurementType))
        );
    }

    @Override
    public List<QuantityMeasurementDTO> getErroredHistory() {
        return QuantityMeasurementDTO.fromEntityList(repository.findByErrorTrue());
    }

    @Override
    public long getOperationCount(OperationType operationType) {
        return repository.countByOperationAndErrorFalse(operationType);
    }

    private QuantityMeasurementDTO executeBinaryOperation(
            OperationType operationType,
            QuantityDTO q1,
            QuantityDTO q2,
            TypedQuantityOperation operation
    ) {
        try {
            String result = executeByMeasurementType(q1, q2, operation);
            return saveSuccess(QuantityMeasurementDTO.success(operationType, q1, q2, result));
        } catch (RuntimeException exception) {
            return handleFailure(operationType, q1, q2, exception);
        }
    }

    private QuantityMeasurementDTO saveSuccess(QuantityMeasurementDTO response) {
        QuantityMeasurementEntity saved = repository.save(response.toEntity());
        return QuantityMeasurementDTO.fromEntity(saved);
    }

    private QuantityMeasurementDTO handleFailure(
            OperationType operationType,
            QuantityDTO q1,
            QuantityDTO q2,
            RuntimeException exception
    ) {
        logger.error("{} failed", operationType, exception);
        repository.save(QuantityMeasurementDTO.failure(
                operationType,
                q1,
                q2,
                buildOperationMessage(operationType, exception)
        ).toEntity());

        if (exception instanceof ArithmeticException arithmeticException) {
            throw arithmeticException;
        }

        if (exception instanceof QuantityMeasurementException quantityMeasurementException) {
            throw quantityMeasurementException;
        }

        throw new QuantityMeasurementException(buildOperationMessage(operationType, exception), exception);
    }

    private String buildOperationMessage(OperationType operationType, RuntimeException exception) {
        return operationType.name().toLowerCase() + " Error: " + exception.getMessage();
    }

    private String executeByMeasurementType(
            QuantityDTO thisQuantity,
            QuantityDTO thatQuantity,
            TypedQuantityOperation operation
    ) {
        String thisType = MeasurementTypeResolver.normalizeMeasurementType(thisQuantity.getMeasurementType());
        String thatType = MeasurementTypeResolver.normalizeMeasurementType(thatQuantity.getMeasurementType());

        if (!thisType.equals(thatType)) {
            throw new QuantityMeasurementException(
                    "Cannot perform arithmetic between different measurement categories: "
                            + thisType + " and " + thatType
            );
        }

        Class<? extends Enum<?>> unitClass = MeasurementTypeResolver.resolveUnitClass(thisType);
        return executeTypedOperation(thisQuantity, thatQuantity, unitClass, operation);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private String executeTypedOperation(
            QuantityDTO thisQuantity,
            QuantityDTO thatQuantity,
            Class<? extends Enum<?>> unitClass,
            TypedQuantityOperation operation
    ) {
        Class typedUnitClass = unitClass;
        Quantity left = createQuantity(thisQuantity, typedUnitClass);
        Quantity right = createQuantity(thatQuantity, typedUnitClass);
        return operation.apply(left, right, typedUnitClass);
    }

    private <U extends Enum<U> & IMeasurable> Quantity<U> createQuantity(QuantityDTO dto, Class<U> unitClass) {
        U unit = MeasurementTypeResolver.resolveUnit(dto.getMeasurementType(), dto.getUnit(), unitClass);
        return new Quantity<>(dto.getValue(), unit);
    }

    private String formatQuantity(Quantity<? extends IMeasurable> quantity) {
        return formatNumber(quantity.getValue()) + " " + quantity.getUnit().getUnitName();
    }

    private String formatNumber(double value) {
        return BigDecimal.valueOf(value).stripTrailingZeros().toPlainString();
    }

    private interface TypedQuantityOperation {
        <U extends Enum<U> & IMeasurable> String apply(Quantity<U> left, Quantity<U> right, Class<U> unitClass);
    }
}
