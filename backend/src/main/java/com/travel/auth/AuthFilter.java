package com.travel.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.dto.ApiResponse;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = "/api/admin/*")
public class AuthFilter implements Filter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        if ("OPTIONS".equalsIgnoreCase(httpReq.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String path = httpReq.getRequestURI();
        if (path.endsWith("/api/admin/login")
                || path.endsWith("/api/admin/logout")
                || path.endsWith("/api/admin/check")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpReq.getSession(false);
        if (session != null && session.getAttribute("admin") != null) {
            chain.doFilter(request, response);
            return;
        }

        httpResp.setStatus(401);
        httpResp.setContentType("application/json;charset=UTF-8");
        ApiResponse<Void> resp = ApiResponse.error(401, "未登录");
        httpResp.getWriter().write(objectMapper.writeValueAsString(resp));
    }
}
