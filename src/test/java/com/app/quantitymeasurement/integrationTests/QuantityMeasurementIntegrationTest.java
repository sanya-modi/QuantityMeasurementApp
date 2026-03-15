package com.app.quantitymeasurement.integrationTests;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuantityMeasurementIntegrationTest {

    private QuantityMeasurementDatabaseRepository repository;
    private QuantityMeasurementController controller;

    @BeforeEach
    void setUp() {
        System.setProperty("db.url", "jdbc:h2:mem:quantitymeasurement_integration_test;DB_CLOSE_DELAY=-1");
        System.setProperty("db.username", "sa");
        System.setProperty("db.password", "");
        System.setProperty("db.pool.initialSize", "1");
        System.setProperty("db.pool.maxSize", "4");
        System.setProperty("db.pool.connectionTimeoutMs", "2000");

        repository = new QuantityMeasurementDatabaseRepository();
        repository.deleteAll();

        controller = new QuantityMeasurementController(new QuantityMeasurementServiceImpl(repository));
    }

    @AfterEach
    void tearDown() {
        if (repository != null) {
            repository.releaseResources();
        }

        System.clearProperty("db.url");
        System.clearProperty("db.username");
        System.clearProperty("db.password");
        System.clearProperty("db.pool.initialSize");
        System.clearProperty("db.pool.maxSize");
        System.clearProperty("db.pool.connectionTimeoutMs");
    }

    @Test
    void testEndToEnd_CompareAndPersistToDatabase() {
        QuantityDTO q1 = new QuantityDTO(10.0, "FOOT", "length");
        QuantityDTO q2 = new QuantityDTO(10.0, "INCH", "length");

        boolean result = controller.performComparison(q1, q2);
        List<QuantityMeasurementEntity> all = repository.getAllMeasurements();

        assertTrue(result);
        assertEquals(1, repository.getTotalCount());
        assertEquals(1, all.size());
        assertEquals("COMPARE", all.get(0).getOperation());
    }

    @Test
    void testEndToEnd_QueryByOperation() {
        controller.performComparison(new QuantityDTO(10.0, "FOOT", "length"),
                new QuantityDTO(10.0, "FOOT", "length"));
        controller.performComparison(new QuantityDTO(10.0, "FOOT", "length"),
                new QuantityDTO(20.0, "FOOT", "length"));

        List<QuantityMeasurementEntity> compareRows = repository.getMeasurementsByOperation("COMPARE");

        assertFalse(compareRows.isEmpty());
        assertEquals(2, compareRows.size());
    }
}