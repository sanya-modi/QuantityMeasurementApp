package com.app.quantitymeasurement.dto;

import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * =========================================================
 * QuantityMeasurementDTO
 * =========================================================
 *
 * UC17 – API Response DTO
 *
 * Represents the result returned to the client.
 * 
 * Client (Swagger/Postman)
 *       │
 *       │ JSON Request
 *       
 *  QuantityInputDTO
 *       │
 *       
 *  Controller
 *       │
 *       
 *  Service Layer
 *       │
 *       
 *  QuantityDTO  (internal conversion logic)
 *       │
 *       
 *  Entity + Repository (DB)
 *       │
 *       
 *  QuantityMeasurementDTO
 *       │
 *       
 *  Controller
 *       │
 *       
 * JSON Response to Client
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "QuantityMeasurementDTO", description = "Structured response for quantity operations")
public class QuantityMeasurementDTO {

    private Long id;
    private Double thisValue;
    private String thisUnit;
    private String thisMeasurementType;
    private Double thatValue;
    private String thatUnit;
    private String thatMeasurementType;
    private OperationType operationType;
    private String result;
    private boolean error;
    private String errorMessage;
    private LocalDateTime createdAt;

    public static QuantityMeasurementDTO fromEntity(QuantityMeasurementEntity entity) {
        return new QuantityMeasurementDTO(
                entity.getId(),
                entity.getThisValue(),
                entity.getThisUnit(),
                entity.getThisMeasurementType(),
                entity.getThatValue(),
                entity.getThatUnit(),
                entity.getThatMeasurementType(),
                entity.getOperation(),
                entity.getResult(),
                entity.isError(),
                entity.getErrorMessage(),
                entity.getCreatedAt()
        );
    }

    public QuantityMeasurementEntity toEntity() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setId(id);
        entity.setOperation(operationType);
        entity.setThisValue(thisValue);
        entity.setThisUnit(thisUnit);
        entity.setThisMeasurementType(thisMeasurementType);
        entity.setThatValue(thatValue);
        entity.setThatUnit(thatUnit);
        entity.setThatMeasurementType(thatMeasurementType);
        entity.setResult(result);
        entity.setError(error);
        entity.setErrorMessage(errorMessage);
        entity.setCreatedAt(createdAt);
        return entity;
    }

    public static List<QuantityMeasurementDTO> fromEntityList(List<QuantityMeasurementEntity> entities) {
        return entities.stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    public static List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> dtos) {
        return dtos.stream()
                .map(QuantityMeasurementDTO::toEntity)
                .toList();
    }

    public static QuantityMeasurementDTO success(
            OperationType operationType,
            QuantityDTO thisQuantity,
            QuantityDTO thatQuantity,
            String result
    ) {
        return new QuantityMeasurementDTO(
                null,
                thisQuantity == null ? null : thisQuantity.getValue(),
                thisQuantity == null ? null : thisQuantity.getUnit(),
                thisQuantity == null ? null : thisQuantity.getMeasurementType(),
                thatQuantity == null ? null : thatQuantity.getValue(),
                thatQuantity == null ? null : thatQuantity.getUnit(),
                thatQuantity == null ? null : thatQuantity.getMeasurementType(),
                operationType,
                result,
                false,
                null,
                null
        );
    }

    public static QuantityMeasurementDTO failure(
            OperationType operationType,
            QuantityDTO thisQuantity,
            QuantityDTO thatQuantity,
            String errorMessage
    ) {
        return new QuantityMeasurementDTO(
                null,
                thisQuantity == null ? null : thisQuantity.getValue(),
                thisQuantity == null ? null : thisQuantity.getUnit(),
                thisQuantity == null ? null : thisQuantity.getMeasurementType(),
                thatQuantity == null ? null : thatQuantity.getValue(),
                thatQuantity == null ? null : thatQuantity.getUnit(),
                thatQuantity == null ? null : thatQuantity.getMeasurementType(),
                operationType,
                null,
                true,
                errorMessage,
                null
        );
    }
}
