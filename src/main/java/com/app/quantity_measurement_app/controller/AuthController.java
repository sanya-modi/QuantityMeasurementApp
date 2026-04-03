package com.app.quantity_measurement_app.controller;

import com.app.quantity_measurement_app.config.AppProperties;
import com.app.quantity_measurement_app.dto.AuthResponse;
import com.app.quantity_measurement_app.dto.LoginRequest;
import com.app.quantity_measurement_app.dto.RegisterRequest;
import com.app.quantity_measurement_app.dto.UserDTO;
import com.app.quantity_measurement_app.model.User;
import com.app.quantity_measurement_app.security.CustomUserPrincipal;
import com.app.quantity_measurement_app.security.JwtService;
import com.app.quantity_measurement_app.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication and user management endpoints")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final JwtService jwtService;
    private final AppProperties appProperties;

    public AuthController(
            AuthenticationManager authenticationManager,
            IUserService userService,
            JwtService jwtService,
            AppProperties appProperties
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.appProperties = appProperties;
    }

    @GetMapping
    @Operation(summary = "Auth root info")
    public ResponseEntity<Map<String, String>> authRoot() {
        return ResponseEntity.ok(Map.of(
                "message", "Use POST /auth/login or POST /auth/register for JWT auth, or /oauth2/authorization/google for Google OAuth2"
        ));
    }

    @PostMapping("/register")
    @Operation(summary = "Register a user with email/password and return a JWT")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.registerLocalUser(request.getEmail(), request.getPassword(), request.getName());
        String token = jwtService.generateToken(user);

        logger.info("Registered local user: {}", user.getEmail());
        return ResponseEntity.ok(buildAuthResponse(user, token));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate with email/password and return a JWT")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        User user = userService.updateLastLogin(principal.getUser());
        String token = jwtService.generateToken(user);

        logger.info("Authenticated local user: {}", user.getEmail());
        return ResponseEntity.ok(buildAuthResponse(user, token));
    }

    @GetMapping("/user")
    @Operation(summary = "Get current authenticated user information")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        User user = resolveUser(authentication);
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        UserDTO userDTO = UserDTO.fromUser(user);
        logger.info("Returning user info for: {}", userDTO.getEmail());
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/status")
    @Operation(summary = "Check authentication status")
    public ResponseEntity<Map<String, Object>> getAuthStatus(Authentication authentication) {
        User user = resolveUser(authentication);
        boolean authenticated = user != null;
        Map<String, Object> status = Map.of(
                "authenticated", authenticated,
                "user", authenticated ? UserDTO.fromUser(user) : Map.of()
        );
        return ResponseEntity.ok(status);
    }

    @GetMapping("/login")
    @Operation(summary = "Redirect to Google OAuth2 login")
    public ResponseEntity<Map<String, String>> login() {
        return ResponseEntity.ok(Map.of(
                "message", "Use POST /auth/login for JWT login or /oauth2/authorization/google for Google OAuth2 login",
                "loginUrl", "/oauth2/authorization/google",
                "jwtLoginUrl", "/auth/login",
                "registerUrl", "/auth/register",
                "oauth2LoginUrl", "/oauth2/authorization/google"
        ));
    }

    @GetMapping("/google")
    @Operation(summary = "Start Google OAuth2 login flow")
    public ResponseEntity<Void> googleLogin() {
        return ResponseEntity.status(302)
                .location(URI.create("/oauth2/authorization/google"))
                .build();
    }

    @GetMapping("/google/callback")
    @Operation(summary = "Google OAuth2 callback handler")
    public ResponseEntity<Void> googleCallback(
            Authentication authentication,
            HttpServletRequest request) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserPrincipal) {
            CustomUserPrincipal customUser = (CustomUserPrincipal) authentication.getPrincipal();
            String token = jwtService.generateToken(customUser.getUser());
            String redirectUri = UriComponentsBuilder
                    .fromUriString(appProperties.getOauth2().getRedirectUri())
                    .queryParam("token", token)
                    .build()
                    .toUriString();
            return ResponseEntity.status(302).location(URI.create(redirectUri)).build();
        }

        String query = request.getQueryString();
        if (query != null && !query.isBlank()) {
            return ResponseEntity.status(302)
                    .location(URI.create("/login/oauth2/code/google?" + query))
                    .build();
        }

        String redirectUri = UriComponentsBuilder
                .fromUriString(appProperties.getOauth2().getRedirectUri())
                .queryParam("oauthError", "Google callback did not include an authorization response.")
                .build()
                .toUriString();
        return ResponseEntity.status(302)
                .location(URI.create(redirectUri))
                .build();
    }

    private AuthResponse buildAuthResponse(User user, String token) {
        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationMs() / 1000)
                .user(UserDTO.fromUser(user))
                .build();
    }

    private User resolveUser(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserPrincipal customUserPrincipal) {
            return customUserPrincipal.getUser();
        }
        if (principal instanceof OAuth2User oauth2User) {
            String email = oauth2User.getAttribute("email");
            if (email != null) {
                return userService.findByEmail(email).orElse(null);
            }
        }
        return null;
    }
}
