package com.app.quantitymeasurement;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = QuantityMeasurementApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:quantitymeasurement_test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        }
)
@AutoConfigureMockMvc(addFilters = false)
class QuantityMeasurementAppApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuantityMeasurementRepository repository;

    @BeforeEach
    void clearRepository() {
        repository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
    }

    @Test
    void compareEndpointPersistsAndReturnsResult() throws Exception {
        String request = """
                {
                  "thisQuantityDTO": {"value": 1.0, "unit": "FOOT", "measurementType": "LengthUnit"},
                  "thatQuantityDTO": {"value": 12.0, "unit": "INCH", "measurementType": "LengthUnit"}
                }
                """;

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("true"))
                .andExpect(jsonPath("$.operationType").value("COMPARE"));

        assertEquals(1, repository.countByOperationAndErrorFalse(OperationType.COMPARE));
    }

    @Test
    void actuatorAndSwaggerEndpointsAreAccessible() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));

        mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(status().is3xxRedirection());
    }
}
