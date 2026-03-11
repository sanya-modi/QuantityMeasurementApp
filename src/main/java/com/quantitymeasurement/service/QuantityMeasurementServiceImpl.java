package com.quantitymeasurement.service;

import com.quantitymeasurement.measurement.Quantity;
import com.quantitymeasurement.measurement.IMeasurable;
import com.quantitymeasurement.measurement.length.LengthUnit;
import com.quantitymeasurement.measurement.weight.WeightUnit;
import com.quantitymeasurement.measurement.volume.VolumeUnit;
import com.quantitymeasurement.measurement.temperature.TemperatureUnit;

import com.quantitymeasurement.dto.QuantityDTO;
import com.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.quantitymeasurement.exception.QuantityMeasurementException;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    // ---------- Helper Method ----------
    private IMeasurable getUnit(String measurementType, String unitName) {

        switch (measurementType.toLowerCase()) {

            case "length":
                return LengthUnit.valueOf(unitName.toUpperCase());

            case "weight":
                return WeightUnit.valueOf(unitName.toUpperCase());

            case "volume":
                return VolumeUnit.valueOf(unitName.toUpperCase());

            case "temperature":
                return TemperatureUnit.valueOf(unitName.toUpperCase());

            default:
                throw new QuantityMeasurementException("Invalid measurement type");
        }
    }

    private Quantity<IMeasurable> createQuantity(QuantityDTO dto) {

        IMeasurable unit = getUnit(dto.getMeasurementType(), dto.getUnit());

        return new Quantity<>(dto.getValue(), unit);
    }

    // ---------- Compare ----------
    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        try {

            Quantity<IMeasurable> quantity1 = createQuantity(q1);
            Quantity<IMeasurable> quantity2 = createQuantity(q2);

            boolean result = quantity1.equals(quantity2);

            repository.save(new QuantityMeasurementEntity(
                    "COMPARE", String.valueOf(result)));

            return result;

        } catch (Exception e) {
            throw new QuantityMeasurementException(e.getMessage());
        }
    }

    // ---------- Convert ----------
    @Override
    public QuantityDTO convert(QuantityDTO quantityDTO, String targetUnit) {

        try {

            Quantity<IMeasurable> quantity = createQuantity(quantityDTO);

            IMeasurable target =
                    getUnit(quantityDTO.getMeasurementType(), targetUnit);

            Quantity<IMeasurable> result = quantity.convertTo(target);

            repository.save(new QuantityMeasurementEntity(
                    "CONVERT", result.toString()));

            return new QuantityDTO(
                    result.getValue(),
                    targetUnit,
                    quantityDTO.getMeasurementType());

        } catch (Exception e) {
            throw new QuantityMeasurementException(e.getMessage());
        }
    }

    // ---------- Add ----------
    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {

        try {

            Quantity<IMeasurable> quantity1 = createQuantity(q1);
            Quantity<IMeasurable> quantity2 = createQuantity(q2);

            Quantity<IMeasurable> result = quantity1.add(quantity2);

            repository.save(new QuantityMeasurementEntity(
                    "ADD", result.toString()));

            return new QuantityDTO(
                    result.getValue(),
                    result.getUnit().getUnitName(),
                    q1.getMeasurementType());

        } catch (Exception e) {
            throw new QuantityMeasurementException(e.getMessage());
        }
    }

    // ---------- Subtract ----------
    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {

        try {

            Quantity<IMeasurable> quantity1 = createQuantity(q1);
            Quantity<IMeasurable> quantity2 = createQuantity(q2);

            Quantity<IMeasurable> result = quantity1.subtract(quantity2);

            repository.save(new QuantityMeasurementEntity(
                    "SUBTRACT", result.toString()));

            return new QuantityDTO(
                    result.getValue(),
                    result.getUnit().getUnitName(),
                    q1.getMeasurementType());

        } catch (Exception e) {
            throw new QuantityMeasurementException(e.getMessage());
        }
    }

    // ---------- Divide ----------
    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {

        try {

            Quantity<IMeasurable> quantity1 = createQuantity(q1);
            Quantity<IMeasurable> quantity2 = createQuantity(q2);

            double result = quantity1.divide(quantity2);

            repository.save(new QuantityMeasurementEntity(
                    "DIVIDE", String.valueOf(result)));

            return result;

        } catch (Exception e) {
            throw new QuantityMeasurementException(e.getMessage());
        }
    }
}