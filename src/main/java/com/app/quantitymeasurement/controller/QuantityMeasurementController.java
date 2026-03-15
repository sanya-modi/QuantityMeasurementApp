package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementController {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementController.class);

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(
            IQuantityMeasurementService service) {
        this.service = service;
        logger.info("QuantityMeasurementController initialized");
    }

    public boolean performComparison(
            QuantityDTO q1, QuantityDTO q2) {

        return service.compare(q1, q2);
    }

    public QuantityDTO performConversion(
            QuantityDTO quantity,
            String targetUnit) {

        return service.convert(quantity, targetUnit);
    }

    public QuantityDTO performAddition(
            QuantityDTO q1, QuantityDTO q2) {

        return service.add(q1, q2);
    }

    public QuantityDTO performSubtraction(
            QuantityDTO q1, QuantityDTO q2) {

        return service.subtract(q1, q2);
    }

    public double performDivision(
            QuantityDTO q1, QuantityDTO q2) {

        return service.divide(q1, q2);
    }
}