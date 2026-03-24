package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(
        value = "/api/v1/quantities",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    @PostMapping(value = "/compare", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<QuantityMeasurementDTO> compareQuantities(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.compare(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }

    @PostMapping(value = "/convert", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Convert a quantity to a target unit")
    public ResponseEntity<QuantityMeasurementDTO> convertQuantity(
            @Valid @RequestBody QuantityInputDTO input,
            @Parameter(description = "Optional target unit override") @RequestParam(required = false) String targetUnit
    ) {
        String resolvedTargetUnit = targetUnit;
        if (resolvedTargetUnit == null || resolvedTargetUnit.isBlank()) {
            resolvedTargetUnit = input.getThatQuantityDTO().getUnit();
        }

        return ResponseEntity.ok(service.convert(input.getThisQuantityDTO(), resolvedTargetUnit));
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementDTO> addQuantities(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.add(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }

    @PostMapping(value = "/subtract", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementDTO> subtractQuantities(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.subtract(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }

    @PostMapping(value = "/divide", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Divide one quantity by another")
    public ResponseEntity<QuantityMeasurementDTO> divideQuantities(@Valid @RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.divide(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
    }

    @GetMapping("/history/operation/{operation}")
    @Operation(summary = "Get quantity measurement history by operation")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(@PathVariable String operation) {
        return ResponseEntity.ok(service.getHistoryByOperation(OperationType.fromValue(operation)));
    }

    @GetMapping("/history/type/{measurementType}")
    @Operation(summary = "Get quantity measurement history by measurement type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getMeasurementTypeHistory(@PathVariable String measurementType) {
        return ResponseEntity.ok(service.getHistoryByMeasurementType(measurementType));
    }

    @GetMapping("/history/errored")
    @Operation(summary = "Get quantity measurement history with errors")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErroredHistory() {
        return ResponseEntity.ok(service.getErroredHistory());
    }

    @GetMapping("/count/{operation}")
    @Operation(summary = "Get successful operation count")
    public ResponseEntity<Map<String, Object>> getOperationCount(@PathVariable String operation) {
        OperationType operationType = OperationType.fromValue(operation);
        return ResponseEntity.ok(Map.of(
                "operation", operationType,
                "count", service.getOperationCount(operationType)
        ));
    }
}
