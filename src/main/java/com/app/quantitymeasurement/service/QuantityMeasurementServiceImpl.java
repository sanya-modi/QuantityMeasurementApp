package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementServiceImpl
        implements IQuantityMeasurementService {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(
            IQuantityMeasurementRepository repository) {
        this.repository = repository;
        logger.info("QuantityMeasurementService initialized");
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        boolean result = Double.compare(q1.getValue(), q2.getValue()) == 0;
        String inputSnapshot = String.format(
                "%s %s %.4f vs %s %s %.4f",
                q1.getMeasurementType(), q1.getUnit(), q1.getValue(),
                q2.getMeasurementType(), q2.getUnit(), q2.getValue()
        );

        repository.save(
                new QuantityMeasurementEntity(
                        "COMPARE",
                    inputSnapshot,
                        String.valueOf(result),
                        false
                )
        );

            logger.debug("Saved COMPARE operation result={}", result);

        return result;
    }

    @Override
    public QuantityDTO convert(QuantityDTO quantity, String targetUnit) {
        return quantity;
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {
        return q1;
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {
        return q1;
    }

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {
        return 0;
    }
}