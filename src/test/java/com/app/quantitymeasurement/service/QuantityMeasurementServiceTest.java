package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuantityMeasurementServiceTest {

    private QuantityMeasurementCacheRepository repository;
    private QuantityMeasurementServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = QuantityMeasurementCacheRepository.getInstance();
        repository.deleteAll();
        service = new QuantityMeasurementServiceImpl(repository);
    }

    @Test
    void testCompare_PersistsEntityAndReturnsTrue() {
        QuantityDTO q1 = new QuantityDTO(10.0, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(10.0, "INCH", "length");

        boolean result = service.compare(q1, q2);
        List<QuantityMeasurementEntity> stored = repository.getAllMeasurements();

        assertTrue(result);
        assertEquals(1, stored.size());
        assertEquals("COMPARE", stored.get(0).getOperation());
    }

    @Test
    void testCompare_ReturnsFalseForDifferentValues() {
        QuantityDTO q1 = new QuantityDTO(10.0, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(12.0, "FOOT", "length");

        boolean result = service.compare(q1, q2);

        assertFalse(result);
    }

    @Test
    void testConvertAddSubtractDivide_ExecutionCompatibility() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCH", "length");

        assertNotNull(service.convert(q1, "INCH"));
        assertNotNull(service.add(q1, q2));
        assertNotNull(service.subtract(q1, q2));
        assertTrue(service.divide(q1, q2) >= 0);
    }
}