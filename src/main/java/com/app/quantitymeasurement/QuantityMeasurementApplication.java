package com.app.quantitymeasurement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Quantity Measurement API",
                version = "v1",
                description = "Spring Boot REST API for quantity measurement operations",
                contact = @Contact(name = "Quantity Measurement Team"),
                license = @License(name = "MIT")
        )
)
public class QuantityMeasurementApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuantityMeasurementApplication.class, args);
    }
}
