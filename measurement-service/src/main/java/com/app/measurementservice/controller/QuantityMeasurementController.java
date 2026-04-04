package com.app.measurementservice.controller;

import com.app.measurementservice.dto.QuantityInputDTO;
import com.app.measurementservice.dto.QuantityMeasurementDTO;
import com.app.measurementservice.service.IQuantityMeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {

    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementController.class);

    @Autowired
    private IQuantityMeasurementService service;

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<QuantityMeasurementDTO> compareQuantities(
            @Valid @RequestBody QuantityInputDTO input,
            Authentication authentication) {
        Long userId = extractUserId(authentication);
        logger.info("POST /compare by user: {}", userId);
        QuantityMeasurementDTO result = service.compareQuantities(
                input.getThisQuantityDTO(), input.getThatQuantityDTO(), userId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert a quantity to a target unit")
    public ResponseEntity<QuantityMeasurementDTO> convertQuantity(
            @Valid @RequestBody QuantityInputDTO input,
            Authentication authentication) {
        Long userId = extractUserId(authentication);
        logger.info("POST /convert by user: {}", userId);
        QuantityMeasurementDTO result = service.convertQuantity(
                input.getThisQuantityDTO(), input.getThatQuantityDTO(), userId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementDTO> addQuantities(
            @Valid @RequestBody QuantityInputDTO input,
            Authentication authentication) {
        Long userId = extractUserId(authentication);
        logger.info("POST /add by user: {}", userId);
        QuantityMeasurementDTO result = service.addQuantities(
                input.getThisQuantityDTO(), input.getThatQuantityDTO(), userId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementDTO> subtractQuantities(
            @Valid @RequestBody QuantityInputDTO input,
            Authentication authentication) {
        Long userId = extractUserId(authentication);
        logger.info("POST /subtract by user: {}", userId);
        QuantityMeasurementDTO result = service.subtractQuantities(
                input.getThisQuantityDTO(), input.getThatQuantityDTO(), userId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/multiply")
    @Operation(summary = "Multiply two quantities")
    public ResponseEntity<QuantityMeasurementDTO> multiplyQuantities(
            @Valid @RequestBody QuantityInputDTO input,
            Authentication authentication) {
        Long userId = extractUserId(authentication);
        logger.info("POST /multiply by user: {}", userId);
        QuantityMeasurementDTO result = service.multiplyQuantities(
                input.getThisQuantityDTO(), input.getThatQuantityDTO(), userId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityMeasurementDTO> divideQuantities(
            @Valid @RequestBody QuantityInputDTO input,
            Authentication authentication) {
        Long userId = extractUserId(authentication);
        logger.info("POST /divide by user: {}", userId);
        QuantityMeasurementDTO result = service.divideQuantities(
                input.getThisQuantityDTO(), input.getThatQuantityDTO(), userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/history/operation/{operation}")
    @Operation(summary = "Get measurement history by operation type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(@PathVariable String operation) {
        logger.info("GET /history/operation/{}", operation);
        List<QuantityMeasurementDTO> history = service.getHistoryByOperation(operation);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/history/type/{measurementType}")
    @Operation(summary = "Get measurement history by measurement type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getMeasurementTypeHistory(@PathVariable String measurementType) {
        logger.info("GET /history/type/{}", measurementType);
        List<QuantityMeasurementDTO> history = service.getHistoryByMeasurementType(measurementType);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/count/{operation}")
    @Operation(summary = "Get count of successful operations by type")
    public ResponseEntity<Long> getOperationCount(@PathVariable String operation) {
        logger.info("GET /count/{}", operation);
        long count = service.getCountByOperation(operation);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/history/errored")
    @Operation(summary = "Get error history")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErrorHistory() {
        logger.info("GET /history/errored");
        List<QuantityMeasurementDTO> errors = service.getErrorHistory();
        return ResponseEntity.ok(errors);
    }

    @GetMapping("/my/history")
    @Operation(summary = "Get current user's measurement history")
    public ResponseEntity<List<QuantityMeasurementDTO>> getMyHistory(
            Authentication authentication) {
        Long userId = extractUserId(authentication);
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        logger.info("GET /my/history for user: {}", userId);
        List<QuantityMeasurementDTO> history = service.getUserHistory(userId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/my/history/operation/{operation}")
    @Operation(summary = "Get current user's history by operation")
    public ResponseEntity<List<QuantityMeasurementDTO>> getMyOperationHistory(
            @PathVariable String operation,
            Authentication authentication) {
        Long userId = extractUserId(authentication);
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        logger.info("GET /my/history/operation/{} for user: {}", operation, userId);
        List<QuantityMeasurementDTO> history = service.getUserHistoryByOperation(userId, operation);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/my/history/type/{measurementType}")
    @Operation(summary = "Get current user's history by measurement type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getMyTypeHistory(
            @PathVariable String measurementType,
            Authentication authentication) {
        Long userId = extractUserId(authentication);
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        logger.info("GET /my/history/type/{} for user: {}", measurementType, userId);
        List<QuantityMeasurementDTO> history = service.getUserHistoryByMeasurementType(userId, measurementType);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/my/count/{operation}")
    @Operation(summary = "Get current user's count by operation")
    public ResponseEntity<Long> getMyOperationCount(
            @PathVariable String operation,
            Authentication authentication) {
        Long userId = extractUserId(authentication);
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        logger.info("GET /my/count/{} for user: {}", operation, userId);
        long count = service.getUserCountByOperation(userId, operation);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/my/history/errored")
    @Operation(summary = "Get current user's error history")
    public ResponseEntity<List<QuantityMeasurementDTO>> getMyErrorHistory(
            Authentication authentication) {
        Long userId = extractUserId(authentication);
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        logger.info("GET /my/history/errored for user: {}", userId);
        List<QuantityMeasurementDTO> errors = service.getUserErrorHistory(userId);
        return ResponseEntity.ok(errors);
    }

    @DeleteMapping("/my/history/{id}")
    @Operation(summary = "Delete one history item for the current user")
    public ResponseEntity<Void> deleteMyHistoryItem(
            @PathVariable Long id,
            Authentication authentication) {
        Long userId = extractUserId(authentication);
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        logger.info("DELETE /my/history/{} for user: {}", id, userId);
        service.deleteUserHistoryById(userId, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/my/history")
    @Operation(summary = "Clear all history for the current user")
    public ResponseEntity<Void> clearMyHistory(Authentication authentication) {
        Long userId = extractUserId(authentication);
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        logger.info("DELETE /my/history for user: {}", userId);
        service.clearUserHistory(userId);
        return ResponseEntity.noContent().build();
    }

    private Long extractUserId(Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        try {
            // Extract from authentication credentials (userId put there by JWT filter)
            Object credentials = authentication.getCredentials();
            if (credentials instanceof Long) {
                return (Long) credentials;
            }
            if (credentials instanceof Number) {
                return ((Number) credentials).longValue();
            }
        } catch (Exception e) {
            logger.debug("Could not extract userId from authentication", e);
        }
        return null;
    }
}

