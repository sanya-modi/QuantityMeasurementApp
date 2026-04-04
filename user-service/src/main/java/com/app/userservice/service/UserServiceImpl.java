package com.app.userservice.service;

import com.app.userservice.model.User;
import com.app.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final int LEGACY_OAUTH_SAFE_EMAIL_LENGTH = 255;
    private static final int LEGACY_OAUTH_SAFE_PICTURE_URL_LENGTH = 255;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User saveOrUpdateOAuthUser(String googleId, String email, String name, String givenName,
                                      String familyName, String pictureUrl, String locale, boolean emailVerified) {
        String normalizedGoogleId = normalizeAndLimit(googleId, 128);
        String normalizedEmail = normalizeAndLimit(email, LEGACY_OAUTH_SAFE_EMAIL_LENGTH);
        String normalizedName = normalizeAndLimit(name, 255);
        String normalizedGivenName = normalizeAndLimit(givenName, 255);
        String normalizedFamilyName = normalizeAndLimit(familyName, 255);
        String normalizedPictureUrl = normalizeAndLimit(pictureUrl, LEGACY_OAUTH_SAFE_PICTURE_URL_LENGTH);
        String normalizedLocale = normalizeAndLimit(locale, 32);

        Optional<User> existingUser = userRepository.findByGoogleId(normalizedGoogleId);
        if (existingUser.isEmpty()) {
            existingUser = userRepository.findByEmail(normalizedEmail);
        }

        User user = existingUser.orElseGet(User::new);
        user.setGoogleId(normalizedGoogleId);
        user.setEmail(normalizedEmail);
        user.setName(normalizedName);
        user.setGivenName(normalizedGivenName);
        user.setFamilyName(normalizedFamilyName);
        user.setPictureUrl(normalizedPictureUrl);
        user.setLocale(normalizedLocale);
        user.setEmailVerified(emailVerified);
        user.setAuthProvider(user.getPassword() != null ? User.AuthProvider.HYBRID : User.AuthProvider.GOOGLE);
        user.setEnabled(true);
        user.setLastLoginAt(LocalDateTime.now());

        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }

        User savedUser = userRepository.save(user);
        logger.info("{} OAuth user: {}", existingUser.isPresent() ? "Updated existing" : "Created new", normalizedEmail);
        return savedUser;
    }

    @Override
    @Transactional
    public User registerLocalUser(String email, String password, String name) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (user.getPassword() != null) {
                throw new IllegalArgumentException("User already exists with email: " + email);
            }

            user.setPassword(passwordEncoder.encode(password));
            user.setName(name);
            user.setGivenName(name);
            user.setAuthProvider(User.AuthProvider.HYBRID);
            user.setLastLoginAt(LocalDateTime.now());
            logger.info("Enabled local login for existing OAuth user: {}", email);
            return userRepository.save(user);
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .givenName(name)
                .emailVerified(false)
                .authProvider(User.AuthProvider.LOCAL)
                .enabled(true)
                .lastLoginAt(LocalDateTime.now())
                .build();

        logger.info("Created local user: {}", email);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User updateLastLogin(User user) {
        user.setLastLoginAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        logger.info("Deleted user with id: {}", id);
    }

    private String normalizeText(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }

    private String normalizeAndLimit(String value, int maxLength) {
        String normalized = normalizeText(value);
        if (normalized == null) {
            return null;
        }
        return normalized.length() <= maxLength ? normalized : normalized.substring(0, maxLength);
    }
}

