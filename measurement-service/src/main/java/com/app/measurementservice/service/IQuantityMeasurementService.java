package com.app.measurementservice.service;

import com.app.measurementservice.dto.QuantityDTO;
import com.app.measurementservice.dto.QuantityMeasurementDTO;

import java.util.List;

public interface IQuantityMeasurementService {

    QuantityMeasurementDTO compareQuantities(QuantityDTO quantity1, QuantityDTO quantity2, Long userId);

    QuantityMeasurementDTO convertQuantity(QuantityDTO quantity1, QuantityDTO quantity2, Long userId);

    QuantityMeasurementDTO addQuantities(QuantityDTO quantity1, QuantityDTO quantity2, Long userId);

    QuantityMeasurementDTO subtractQuantities(QuantityDTO quantity1, QuantityDTO quantity2, Long userId);

    QuantityMeasurementDTO multiplyQuantities(QuantityDTO quantity1, QuantityDTO quantity2, Long userId);

    QuantityMeasurementDTO divideQuantities(QuantityDTO quantity1, QuantityDTO quantity2, Long userId);

    List<QuantityMeasurementDTO> getHistoryByOperation(String operation);

    List<QuantityMeasurementDTO> getHistoryByMeasurementType(String measurementType);

    long getCountByOperation(String operation);

    List<QuantityMeasurementDTO> getErrorHistory();

    List<QuantityMeasurementDTO> getUserHistory(Long userId);

    List<QuantityMeasurementDTO> getUserHistoryByOperation(Long userId, String operation);

    List<QuantityMeasurementDTO> getUserHistoryByMeasurementType(Long userId, String measurementType);

    long getUserCountByOperation(Long userId, String operation);

    List<QuantityMeasurementDTO> getUserErrorHistory(Long userId);

    void deleteUserHistoryById(Long userId, Long historyId);

    void clearUserHistory(Long userId);
}

