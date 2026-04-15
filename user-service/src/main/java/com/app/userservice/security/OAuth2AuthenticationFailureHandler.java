package com.app.userservice.security;

import com.app.userservice.config.AppProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthenticationFailureHandler.class);
    private static final String DEFAULT_REDIRECT_URI = "http://localhost:3000/oauth-callback";

    private final AppProperties appProperties;

    public OAuth2AuthenticationFailureHandler(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        String message = exception.getMessage() == null ? "Google login failed" : exception.getMessage();
        log.warn("OAuth2 login failed: {}", message);
        String targetUrl = UriComponentsBuilder
                .fromUriString(resolveRedirectUri())
            .queryParam("error", message)
                .build(true)
                .toUriString();

        response.sendRedirect(targetUrl);
    }

    private String resolveRedirectUri() {
        String configured = appProperties.getOauth2().getRedirectUri();
        if (configured == null || configured.isBlank()) {
            return DEFAULT_REDIRECT_URI;
        }
        try {
            UriComponentsBuilder.fromUriString(configured).build(true);
            return configured;
        } catch (Exception ex) {
            log.warn("Invalid app.oauth2.redirect-uri configured: {}. Falling back to default.", configured);
            return DEFAULT_REDIRECT_URI;
        }
    }
}
