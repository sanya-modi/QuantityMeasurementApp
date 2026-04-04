package com.app.userservice.controller;

import com.app.userservice.config.AppProperties;
import com.app.userservice.dto.AuthResponse;
import com.app.userservice.dto.LoginRequest;
import com.app.userservice.dto.RegisterRequest;
import com.app.userservice.dto.UserDTO;
import com.app.userservice.model.User;
import com.app.userservice.security.CustomUserPrincipal;
import com.app.userservice.security.JwtService;
import com.app.userservice.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                "message", "Use POST /auth/login or POST /auth/register for JWT auth"
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
        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(401).build();
        }
        
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        User user = principal.getUser();
        UserDTO userDTO = UserDTO.fromUser(user);
        logger.info("Returning user info for: {}", userDTO.getEmail());
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/status")
    @Operation(summary = "Check authentication status")
    public ResponseEntity<Map<String, Object>> getAuthStatus(Authentication authentication) {
        boolean authenticated = authentication != null && authentication.isAuthenticated();
        Map<String, Object> status = Map.of(
                "authenticated", authenticated,
                "user", authenticated && authentication.getPrincipal() instanceof CustomUserPrincipal
                        ? UserDTO.fromUser(((CustomUserPrincipal) authentication.getPrincipal()).getUser())
                        : Map.of()
        );
        return ResponseEntity.ok(status);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout user")
    public ResponseEntity<Map<String, String>> logout() {
        logger.info("User logged out");
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    private AuthResponse buildAuthResponse(User user, String token) {
        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationMs() / 1000)
                .user(UserDTO.fromUser(user))
                .build();
    }
}

