package com.travel.auth;

import com.travel.dto.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private HttpSession session;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        AuthConfig config = new AuthConfig();
        config.setUsername("admin");
        config.setPassword("admin123");
        authService = new AuthService(config);
    }

    @Test
    void loginWithCorrectCredentials() {
        LoginRequest req = new LoginRequest();
        req.setUsername("admin");
        req.setPassword("admin123");

        boolean result = authService.login(req, session);
        assertTrue(result);
        verify(session).setAttribute(eq("admin"), eq("admin"));
    }

    @Test
    void loginWithWrongPassword() {
        LoginRequest req = new LoginRequest();
        req.setUsername("admin");
        req.setPassword("wrong");

        boolean result = authService.login(req, session);
        assertFalse(result);
    }

    @Test
    void loginWithWrongUsername() {
        LoginRequest req = new LoginRequest();
        req.setUsername("hacker");
        req.setPassword("admin123");

        boolean result = authService.login(req, session);
        assertFalse(result);
    }
}
