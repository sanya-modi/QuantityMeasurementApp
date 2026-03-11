package com;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.quantitymeasurement.controller.QuantityMeasurementController;
import com.quantitymeasurement.dto.QuantityDTO;
import com.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.quantitymeasurement.service.QuantityMeasurementServiceImpl;

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

    // ENTITY LAYER TESTS

    @Test
    void testQuantityEntity_SingleOperandConstruction() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity("CONVERT", "12 INCH");

        assertFalse(entity.hasError());
        assertEquals("12 INCH", entity.getResult());
    }

    @Test
    void testQuantityEntity_ErrorConstruction() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity("Invalid operation");

        assertTrue(entity.hasError());
    }

    // SERVICE LAYER TESTS

    @Test
    void testService_CompareEquality_SameUnit_Success() {

        QuantityDTO q1 = new QuantityDTO(10, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(10, "FOOT", "length");

        boolean result = service.compare(q1, q2);

        assertTrue(result);
    }

    @Test
    void testService_CompareEquality_DifferentUnit_Success() {

        QuantityDTO q1 = new QuantityDTO(1, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(12, "INCH", "length");

        boolean result = service.compare(q1, q2);

        assertTrue(result);
    }

    @Test
    void testService_Convert_Success() {

        QuantityDTO q1 = new QuantityDTO(1, "FOOT", "length");

        QuantityDTO result = service.convert(q1, "INCH");

        assertEquals(12.0, result.getValue());
    }

    @Test
    void testService_Add_Success() {

        QuantityDTO q1 = new QuantityDTO(1, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(12, "INCH", "length");

        QuantityDTO result = service.add(q1, q2);

        assertEquals(2.0, result.getValue());
    }

    @Test
    void testService_Subtract_Success() {

        QuantityDTO q1 = new QuantityDTO(2, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(12, "INCH", "length");

        QuantityDTO result = service.subtract(q1, q2);

        assertEquals(1.0, result.getValue());
    }

    @Test
    void testService_Divide_Success() {

        QuantityDTO q1 = new QuantityDTO(10, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(2, "FOOT", "length");

        double result = service.divide(q1, q2);

        assertEquals(5.0, result);
    }

    @Test
    void testService_Divide_ByZero_Error() {

        QuantityDTO q1 = new QuantityDTO(10, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(0, "FOOT", "length");

        assertThrows(RuntimeException.class,
                () -> service.divide(q1, q2));
    }

    // CONTROLLER LAYER TESTS

    @Test
    void testController_DemonstrateEquality_Success() {

        QuantityDTO q1 = new QuantityDTO(1, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(12, "INCH", "length");

        boolean result = controller.performComparison(q1, q2);

        assertTrue(result);
    }

    @Test
    void testController_DemonstrateConversion_Success() {

        QuantityDTO q1 = new QuantityDTO(1, "FOOT", "length");

        QuantityDTO result =
                controller.performConversion(q1, "INCH");

        assertEquals(12.0, result.getValue());
    }

    @Test
    void testController_DemonstrateAddition_Success() {

        QuantityDTO q1 = new QuantityDTO(1, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(12, "INCH", "length");

        QuantityDTO result =
                controller.performAddition(q1, q2);

        assertEquals(2.0, result.getValue());
    }

    @Test
    void testController_DemonstrateAddition_Error() {

        QuantityDTO q1 = new QuantityDTO(100, "CELSIUS", "temperature");
        QuantityDTO q2 = new QuantityDTO(50, "CELSIUS", "temperature");

        assertThrows(RuntimeException.class,
                () -> controller.performAddition(q1, q2));
    }

    // LAYER SEPARATION TEST

    @Test
    void testLayerSeparation_ServiceIndependence() {

        QuantityDTO q1 = new QuantityDTO(1, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(12, "INCH", "length");

        boolean result = service.compare(q1, q2);

        assertTrue(result);
    }

}