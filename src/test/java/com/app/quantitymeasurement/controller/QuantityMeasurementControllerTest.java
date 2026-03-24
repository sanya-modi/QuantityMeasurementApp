package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.exception.GlobalExceptionHandler;
import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = QuantityMeasurementController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                UserDetailsServiceAutoConfiguration.class,
                ServletWebSecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class
        }
)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IQuantityMeasurementService service;

    @Test
    void compareEndpointReturnsStructuredResponse() throws Exception {
        String request = """
                {
                  "thisQuantityDTO": {"value": 1.0, "unit": "FOOT", "measurementType": "LengthUnit"},
                  "thatQuantityDTO": {"value": 12.0, "unit": "INCH", "measurementType": "LengthUnit"}
                }
                """;

        when(service.compare(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new QuantityMeasurementDTO(
                        1L, 1.0, "FOOT", "LengthUnit",
                        12.0, "INCH", "LengthUnit",
                        OperationType.COMPARE, "true", false, null, null
                ));

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationType").value("COMPARE"))
                .andExpect(jsonPath("$.result").value("true"))
                .andExpect(jsonPath("$.error").value(false));

        verify(service).compare(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void invalidInputReturnsBadRequest() throws Exception {
        String invalidRequest = """
                {
                  "thisQuantityDTO": {"value": 1.0, "unit": "INVALID", "measurementType": "LengthUnit"},
                  "thatQuantityDTO": {"value": 12.0, "unit": "INCH", "measurementType": "LengthUnit"}
                }
                """;

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Quantity Measurement Error"));
    }

    @Test
    void historyEndpointReturnsMeasurements() throws Exception {
        when(service.getHistoryByOperation(OperationType.COMPARE))
                .thenReturn(List.of(new QuantityMeasurementDTO(
                        1L, 1.0, "FOOT", "LengthUnit",
                        12.0, "INCH", "LengthUnit",
                        OperationType.COMPARE, "true", false, null, null
                )));

        mockMvc.perform(get("/api/v1/quantities/history/operation/COMPARE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].operationType").value("COMPARE"))
                .andExpect(jsonPath("$[0].result").value("true"));
    }
}
