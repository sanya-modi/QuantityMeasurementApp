package com.app.quantitymeasurement.repository;

import java.util.List;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {

    void save(QuantityMeasurementEntity entity);

    List<QuantityMeasurementEntity> getAllMeasurements();

    List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);

    List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType);

    long getTotalCount();

    void deleteAll();

    default String getPoolStatistics() {
        return "Connection pool statistics are not available for this repository";
    }

    default void releaseResources() {
        // No resources to release by default.
    }

}