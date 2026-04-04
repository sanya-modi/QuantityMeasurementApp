package com.app.userservice.security;

import com.app.userservice.config.AppProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final AppProperties appProperties;

    public OAuth2AuthenticationFailureHandler(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        String message = exception.getMessage() == null ? "Google login failed" : exception.getMessage();
        String targetUrl = UriComponentsBuilder
                .fromUriString(appProperties.getOauth2().getRedirectUri())
            .queryParam("error", message)
                .build(true)
                .toUriString();

        response.sendRedirect(targetUrl);
    }
}
