package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuantityMeasurementServiceTest {

    @Mock
    private QuantityMeasurementRepository repository;

    @InjectMocks
    private QuantityMeasurementServiceImpl service;

    @BeforeEach
    void setUp() {
        when(repository.save(org.mockito.ArgumentMatchers.any(QuantityMeasurementEntity.class)))
                .thenAnswer(invocation -> {
                    QuantityMeasurementEntity entity = invocation.getArgument(0);
                    entity.setId(1L);
                    return entity;
                });
    }

    @Test
    void comparePersistsSuccessAndReturnsStructuredResult() {
        QuantityMeasurementDTO result = service.compare(
                new QuantityDTO(1.0, "FOOT", "LengthUnit", null),
                new QuantityDTO(12.0, "INCH", "LengthUnit", null)
        );

        assertEquals(OperationType.COMPARE, result.getOperationType());
        assertEquals("true", result.getResult());
        verify(repository).save(argThat(successfulOperation(OperationType.COMPARE)));
    }

    @Test
    void convertUsesExistingBusinessLogic() {
        QuantityMeasurementDTO result = service.convert(
                new QuantityDTO(1.0, "FOOT", "LengthUnit", null),
                "INCH"
        );

        assertEquals("12 INCH", result.getResult());
        verify(repository).save(argThat(successfulOperation(OperationType.CONVERT)));
    }

    @Test
    void incompatibleCategoriesPersistErrorAndThrowException() {
        assertThrows(QuantityMeasurementException.class, () -> service.add(
                new QuantityDTO(1.0, "FOOT", "LengthUnit", null),
                new QuantityDTO(1.0, "KILOGRAM", "WeightUnit", null)
        ));

        verify(repository).save(argThat(entity -> entity.isError() && entity.getOperation() == OperationType.ADD));
    }

    private ArgumentMatcher<QuantityMeasurementEntity> successfulOperation(OperationType operationType) {
        return entity -> entity.getOperation() == operationType && !entity.isError();
    }
}
