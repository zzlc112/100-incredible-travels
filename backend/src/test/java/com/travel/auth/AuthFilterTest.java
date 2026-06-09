package com.travel.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

class AuthFilterTest {

    private AuthFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    @BeforeEach
    void setUp() {
        filter = new AuthFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
    }

    @Test
    void optionsRequestShouldPass() throws Exception {
        when(request.getMethod()).thenReturn("OPTIONS");

        filter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }

    @Test
    void loginPathShouldPass() throws Exception {
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestURI()).thenReturn("/api/admin/login");

        filter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }

    @Test
    void authenticatedSessionShouldPass() throws Exception {
        HttpSession session = mock(HttpSession.class);
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/api/admin/travels");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("admin")).thenReturn("admin");

        filter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }

    @Test
    void noSessionShouldReturn401() throws Exception {
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/api/admin/travels");
        when(request.getSession(false)).thenReturn(null);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        filter.doFilter(request, response, chain);
        verify(response).setStatus(401);
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void sessionWithoutAdminShouldReturn401() throws Exception {
        HttpSession session = mock(HttpSession.class);
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/api/admin/travels");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("admin")).thenReturn(null);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        filter.doFilter(request, response, chain);
        verify(response).setStatus(401);
        verify(chain, never()).doFilter(request, response);
    }
}
