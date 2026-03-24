package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findByOperation(OperationType operation);

    List<QuantityMeasurementEntity> findByThisMeasurementType(String thisMeasurementType);

    List<QuantityMeasurementEntity> findByCreatedAtAfter(LocalDateTime date);

    @Query("select measurement from QuantityMeasurementEntity measurement "
            + "where measurement.operation = :operation and measurement.error = false")
    List<QuantityMeasurementEntity> findSuccessfulByOperation(@Param("operation") OperationType operation);

    long countByOperationAndErrorFalse(OperationType operation);

    List<QuantityMeasurementEntity> findByErrorTrue();
}
