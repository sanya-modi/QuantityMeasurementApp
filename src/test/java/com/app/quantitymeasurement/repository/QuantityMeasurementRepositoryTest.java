package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class QuantityMeasurementRepositoryTest {

    @Autowired
    private QuantityMeasurementRepository repository;

    @Test
    void findByOperationReturnsMatchingRows() {
        repository.save(createEntity(OperationType.COMPARE, false, "LengthUnit"));
        repository.save(createEntity(OperationType.ADD, false, "LengthUnit"));
        repository.save(createEntity(OperationType.COMPARE, true, "WeightUnit"));

        List<QuantityMeasurementEntity> compareRows = repository.findByOperation(OperationType.COMPARE);

        assertEquals(2, compareRows.size());
    }

    @Test
    void countByOperationAndErrorFalseReturnsOnlySuccessfulRows() {
        repository.save(createEntity(OperationType.ADD, false, "LengthUnit"));
        repository.save(createEntity(OperationType.ADD, false, "LengthUnit"));
        repository.save(createEntity(OperationType.ADD, true, "LengthUnit"));

        assertEquals(2, repository.countByOperationAndErrorFalse(OperationType.ADD));
    }

    private QuantityMeasurementEntity createEntity(OperationType operationType, boolean error, String measurementType) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setOperation(operationType);
        entity.setThisValue(1.0);
        entity.setThisUnit("FOOT");
        entity.setThisMeasurementType(measurementType);
        entity.setThatValue(12.0);
        entity.setThatUnit("INCH");
        entity.setThatMeasurementType(measurementType);
        entity.setResult("true");
        entity.setError(error);
        entity.setErrorMessage(error ? "Sample error" : null);
        return entity;
    }
}
