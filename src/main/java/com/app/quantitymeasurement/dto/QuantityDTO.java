package com.app.quantitymeasurement.dto;

import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.util.MeasurementTypeResolver;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * =========================================================
 * QuantityDTO
 * =========================================================
 *
 * UC17 – DTO Layer
 *
 * Represents a quantity consisting of:
 * - numeric value
 * - measurement unit
 *
 * Used internally between controller and service layers.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "QuantityDTO", description = "A quantity with unit and measurement type")
public class QuantityDTO {

    @NotNull(message = "Value is required")
    private Double value;

    @NotBlank(message = "Unit is required")
    private String unit;

    @NotBlank(message = "Measurement type is required")
    @Pattern(
            regexp = "(?i)length|lengthunit|weight|weightunit|volume|volumeunit|temperature|temperatureunit",
            message = "Measurement type must be LengthUnit, WeightUnit, VolumeUnit, or TemperatureUnit"
    )
    private String measurementType;

    private OperationType operationType;

    @JsonIgnore
    @AssertTrue(message = "Unit must be valid for the specified measurement type")
    public boolean isUnitValidForMeasurementType() {
        if (measurementType == null || measurementType.isBlank() || unit == null || unit.isBlank()) {
            return true;
        }

        return MeasurementTypeResolver.isValidUnitForMeasurementType(measurementType, unit);
    }
}
