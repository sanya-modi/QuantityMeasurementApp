package com.app.quantitymeasurement.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

public class QuantityMeasurementCacheRepository
        implements IQuantityMeasurementRepository {

    private static QuantityMeasurementCacheRepository instance;

    private final List<QuantityMeasurementEntity> cache = new ArrayList<>();

    private QuantityMeasurementCacheRepository() {}

    public static synchronized QuantityMeasurementCacheRepository getInstance() {

        if (instance == null)
            instance = new QuantityMeasurementCacheRepository();

        return instance;
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        cache.add(entity);
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return new ArrayList<>(cache);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        if (operation == null) {
            return List.of();
        }

        return cache.stream()
                .filter(entity -> operation.equalsIgnoreCase(entity.getOperation()))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
        if (measurementType == null) {
            return List.of();
        }

        String normalizedType = measurementType.toLowerCase(Locale.ROOT);

        return cache.stream()
                .filter(entity -> entity.getInput() != null
                        && entity.getInput().toLowerCase(Locale.ROOT).contains(normalizedType))
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalCount() {
        return cache.size();
    }

    @Override
    public void deleteAll() {
        cache.clear();
    }

}