package com.app.userservice.security;

import com.app.userservice.config.AppProperties;
import com.app.userservice.model.User;
import com.app.userservice.service.IUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);
    private static final String DEFAULT_REDIRECT_URI = "http://localhost:3000/oauth-callback";

    private final IUserService userService;
    private final JwtService jwtService;
    private final AppProperties appProperties;

    public OAuth2AuthenticationSuccessHandler(IUserService userService, JwtService jwtService, AppProperties appProperties) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.appProperties = appProperties;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            String googleId = asString(oAuth2User.getAttribute("sub"));
            String email = asString(oAuth2User.getAttribute("email"));
            String name = asString(oAuth2User.getAttribute("name"));
            String givenName = asString(oAuth2User.getAttribute("given_name"));
            String familyName = asString(oAuth2User.getAttribute("family_name"));
            String picture = asString(oAuth2User.getAttribute("picture"));
            String locale = asString(oAuth2User.getAttribute("locale"));
            boolean emailVerified = Boolean.TRUE.equals(oAuth2User.getAttribute("email_verified"));

            if (email == null || email.isBlank()) {
                redirectWithError(response, "Google account did not provide an email.");
                return;
            }

            User user = userService.saveOrUpdateOAuthUser(
                    googleId,
                    email,
                    name,
                    givenName,
                    familyName,
                    picture,
                    locale,
                    emailVerified
            );

            String token = jwtService.generateToken(user);
            String targetUrl = UriComponentsBuilder
                    .fromUriString(resolveRedirectUri())
                    .queryParam("token", token)
                    .build(true)
                    .toUriString();

            response.sendRedirect(targetUrl);
        } catch (Exception ex) {
            log.error("OAuth2 success flow failed", ex);
            redirectWithError(response, "Google login could not be completed.");
        }
    }

    private void redirectWithError(HttpServletResponse response, String message) throws IOException {
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

    private String asString(Object value) {
        return value == null ? null : value.toString();
    }
}
