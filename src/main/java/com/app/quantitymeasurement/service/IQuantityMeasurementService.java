package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.OperationType;
import java.util.List;

public interface IQuantityMeasurementService {

    QuantityMeasurementDTO compare(QuantityDTO q1, QuantityDTO q2);

    QuantityMeasurementDTO convert(QuantityDTO quantity, String targetUnit);

    QuantityMeasurementDTO add(QuantityDTO q1, QuantityDTO q2);

    QuantityMeasurementDTO subtract(QuantityDTO q1, QuantityDTO q2);

    QuantityMeasurementDTO divide(QuantityDTO q1, QuantityDTO q2);

    List<QuantityMeasurementDTO> getHistoryByOperation(OperationType operationType);

    List<QuantityMeasurementDTO> getHistoryByMeasurementType(String measurementType);

    List<QuantityMeasurementDTO> getErroredHistory();

    long getOperationCount(OperationType operationType);
}
