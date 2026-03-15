package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuantityMeasurementControllerTest {

    private QuantityMeasurementController controller;

    @BeforeEach
    void setUp() {
        controller = new QuantityMeasurementController(new StubQuantityMeasurementService());
    }

    @Test
    void testPerformComparison_DelegatesToService() {
        QuantityDTO q1 = new QuantityDTO(10.0, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(10.0, "INCH", "length");

        boolean result = controller.performComparison(q1, q2);

        assertTrue(result);
    }

    @Test
    void testPerformConversion_DelegatesToService() {
        QuantityDTO q = new QuantityDTO(1.0, "FOOT", "length");

        QuantityDTO result = controller.performConversion(q, "INCH");

        assertNotNull(result);
        assertEquals("INCH", result.getUnit());
    }

    @Test
    void testPerformAdditionSubtractionDivision_DelegatesToService() {
        QuantityDTO q1 = new QuantityDTO(5.0, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(2.0, "FOOT", "length");

        assertNotNull(controller.performAddition(q1, q2));
        assertNotNull(controller.performSubtraction(q1, q2));
        assertFalse(controller.performDivision(q1, q2) < 0);
    }

    private static class StubQuantityMeasurementService implements IQuantityMeasurementService {

        @Override
        public boolean compare(QuantityDTO q1, QuantityDTO q2) {
            return Double.compare(q1.getValue(), q2.getValue()) == 0;
        }

        @Override
        public QuantityDTO convert(QuantityDTO quantity, String targetUnit) {
            return new QuantityDTO(quantity.getValue(), targetUnit, quantity.getMeasurementType());
        }

        @Override
        public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {
            return new QuantityDTO(q1.getValue() + q2.getValue(), q1.getUnit(), q1.getMeasurementType());
        }

        @Override
        public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {
            return new QuantityDTO(q1.getValue() - q2.getValue(), q1.getUnit(), q1.getMeasurementType());
        }

        @Override
        public double divide(QuantityDTO q1, QuantityDTO q2) {
            if (q2.getValue() == 0.0) {
                return 0.0;
            }
            return q1.getValue() / q2.getValue();
        }
    }
}