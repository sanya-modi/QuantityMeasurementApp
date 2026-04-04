package com.app.measurementservice.repository;

import com.app.measurementservice.model.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findByOperation(String operation);

    List<QuantityMeasurementEntity> findByThisMeasurementType(String measurementType);

    List<QuantityMeasurementEntity> findByCreatedAtAfter(LocalDateTime date);

    @Query("SELECT q FROM QuantityMeasurementEntity q WHERE q.operation = :operation AND q.error = false")
    List<QuantityMeasurementEntity> findSuccessfulByOperation(@Param("operation") String operation);

    long countByOperationAndErrorFalse(String operation);

    List<QuantityMeasurementEntity> findByErrorTrue();

    List<QuantityMeasurementEntity> findByUserId(Long userId);

    List<QuantityMeasurementEntity> findByUserIdAndOperation(Long userId, String operation);

    List<QuantityMeasurementEntity> findByUserIdAndThisMeasurementType(Long userId, String measurementType);

    long countByUserIdAndOperationAndErrorFalse(Long userId, String operation);

    List<QuantityMeasurementEntity> findByUserIdAndErrorTrue(Long userId);

    Optional<QuantityMeasurementEntity> findByIdAndUserId(Long id, Long userId);

    void deleteByUserId(Long userId);

    void deleteByUserIdAndOperation(Long userId, String operation);

    void deleteByUserIdAndThisMeasurementType(Long userId, String measurementType);

    void deleteByUserIdAndErrorTrue(Long userId);

    @Query("SELECT q FROM QuantityMeasurementEntity q WHERE q.userId = :userId ORDER BY q.createdAt DESC")
    List<QuantityMeasurementEntity> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
}

