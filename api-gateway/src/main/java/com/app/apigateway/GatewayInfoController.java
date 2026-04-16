package com.app.apigateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class GatewayInfoController {

    @GetMapping("/")
    public Map<String, Object> root() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("service", "api-gateway");
        response.put("status", "up");
        response.put("routes", new String[]{
                "/auth/**",
                "/oauth2/**",
                "/login/oauth2/code/**",
                "/api/v1/quantities/**",
                "/actuator/health"
        });
        return response;
    }
}
