package com.app.quantitymeasurement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * =========================================================
 * QuantityInputDTO
 * =========================================================
 *
 * UC17 – API Request DTO
 *
 * Represents the input received from REST API.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "QuantityInputDTO", description = "Input payload containing two quantities")
public class QuantityInputDTO {

    @Valid
    @NotNull(message = "thisQuantityDTO is required")
    private QuantityDTO thisQuantityDTO;

    @Valid
    @NotNull(message = "thatQuantityDTO is required")
    private QuantityDTO thatQuantityDTO;
}
