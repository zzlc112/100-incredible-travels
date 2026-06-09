package com.travel.auth;

import com.travel.dto.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthConfig authConfig;

    public AuthService(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    public boolean login(LoginRequest req, HttpSession session) {
        if (authConfig.getUsername().equals(req.getUsername())
                && authConfig.getPassword().equals(req.getPassword())) {
            session.setAttribute("admin", req.getUsername());
            return true;
        }
        return false;
    }
}
