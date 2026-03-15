package com.app.quantitymeasurement.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementAppLayerTest {

    private QuantityMeasurementController controller;
    private QuantityMeasurementServiceImpl service;

    @BeforeEach
    void setup() {

        QuantityMeasurementCacheRepository repository =
                QuantityMeasurementCacheRepository.getInstance();

        service = new QuantityMeasurementServiceImpl(repository);

        controller = new QuantityMeasurementController(service);
    }

    // SERVICE LAYER TESTS

    @Test
    void testService_Convert_Execution() {

        QuantityDTO q1 = new QuantityDTO(1, "FOOT", "length");

        QuantityDTO result = service.convert(q1, "INCH");

        assertNotNull(result);
    }

    @Test
    void testService_Add_Execution() {

        QuantityDTO q1 = new QuantityDTO(1, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(12, "INCH", "length");

        QuantityDTO result = service.add(q1, q2);

        assertNotNull(result);
    }

    @Test
    void testService_Subtract_Execution() {

        QuantityDTO q1 = new QuantityDTO(2, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(12, "INCH", "length");

        QuantityDTO result = service.subtract(q1, q2);

        assertNotNull(result);
    }

    @Test
    void testService_Divide_Execution() {

        QuantityDTO q1 = new QuantityDTO(10, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(2, "FOOT", "length");

        double result = service.divide(q1, q2);

        assertTrue(result >= 0);
    }

    // CONTROLLER LAYER TESTS

    @Test
    void testController_DemonstrateConversion_Execution() {

        QuantityDTO q1 = new QuantityDTO(1, "FOOT", "length");

        QuantityDTO result =
                controller.performConversion(q1, "INCH");

        assertNotNull(result);
    }

    @Test
    void testController_DemonstrateAddition_Execution() {

        QuantityDTO q1 = new QuantityDTO(1, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(12, "INCH", "length");

        QuantityDTO result =
                controller.performAddition(q1, q2);

        assertNotNull(result);
    }

}