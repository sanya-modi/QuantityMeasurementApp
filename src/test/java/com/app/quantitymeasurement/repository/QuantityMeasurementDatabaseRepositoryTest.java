package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuantityMeasurementDatabaseRepositoryTest {

    private QuantityMeasurementDatabaseRepository repository;

    @BeforeEach
    void setUp() {
        System.setProperty("db.url", "jdbc:h2:mem:quantitymeasurement_repo_test;DB_CLOSE_DELAY=-1");
        System.setProperty("db.username", "sa");
        System.setProperty("db.password", "");
        System.setProperty("db.pool.initialSize", "1");
        System.setProperty("db.pool.maxSize", "4");
        System.setProperty("db.pool.connectionTimeoutMs", "2000");

        repository = new QuantityMeasurementDatabaseRepository();
        repository.deleteAll();
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
    void testSaveAndGetAllMeasurements() {
        repository.save(new QuantityMeasurementEntity("COMPARE", "length FOOT", "true", false));
        repository.save(new QuantityMeasurementEntity("ADD", "volume LITRE", "2.0", false));

        List<QuantityMeasurementEntity> all = repository.getAllMeasurements();

        assertEquals(2, all.size());
        assertEquals("COMPARE", all.get(0).getOperation());
    }

    @Test
    void testGetMeasurementsByOperation() {
        repository.save(new QuantityMeasurementEntity("COMPARE", "length FOOT", "true", false));
        repository.save(new QuantityMeasurementEntity("ADD", "length INCH", "24.0", false));
        repository.save(new QuantityMeasurementEntity("COMPARE", "weight KILOGRAM", "false", false));

        List<QuantityMeasurementEntity> compareItems = repository.getMeasurementsByOperation("COMPARE");

        assertEquals(2, compareItems.size());
    }

    @Test
    void testGetMeasurementsByType() {
        repository.save(new QuantityMeasurementEntity("ADD", "length FOOT", "2.0", false));
        repository.save(new QuantityMeasurementEntity("ADD", "volume LITRE", "3.0", false));

        List<QuantityMeasurementEntity> typeItems = repository.getMeasurementsByType("length");

        assertEquals(1, typeItems.size());
        assertTrue(typeItems.get(0).getInput().toLowerCase().contains("length"));
    }

    @Test
    void testCountAndDeleteAll() {
        repository.save(new QuantityMeasurementEntity("COMPARE", "length FOOT", "true", false));
        repository.save(new QuantityMeasurementEntity("SUBTRACT", "length INCH", "1.5", false));

        assertEquals(2, repository.getTotalCount());

        repository.deleteAll();

        assertEquals(0, repository.getTotalCount());
    }

    @Test
    void testPoolStatistics() {
        String stats = repository.getPoolStatistics();

        assertTrue(stats.contains("total="));
        assertTrue(stats.contains("active="));
        assertTrue(stats.contains("idle="));
    }
}