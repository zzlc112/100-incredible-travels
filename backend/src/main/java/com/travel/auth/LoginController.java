package com.travel.auth;

import com.travel.dto.ApiResponse;
import com.travel.dto.LoginRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@Valid @RequestBody LoginRequest body, HttpSession session) {
        if (authService.login(body, session)) {
            return ApiResponse.success("登录成功");
        }
        return ApiResponse.error(401, "用户名或密码错误");
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(HttpSession session) {
        session.invalidate();
        return ApiResponse.success("已退出登录");
    }

    @GetMapping("/check")
    public ApiResponse<String> check(HttpSession session) {
        if (session.getAttribute("admin") != null) {
            return ApiResponse.success("已登录");
        }
        return ApiResponse.error(401, "未登录");
    }
}
