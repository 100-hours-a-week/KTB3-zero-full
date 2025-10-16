package com.example.week04_hw.support;

import com.example.week04_hw.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 로그인 필요 경로에서 세션 검증.
 * - 로그인 상태면 통과
 * - 비로그인:
 *   - JSON 요청이면 401 + JSON 본문
 *   - 그 외(HTML)면 /users/login?next=... 로 리다이렉트
 */
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        boolean loggedIn = session != null &&
                session.getAttribute(UserService.SESSION_USERID) != null;

        if (loggedIn) return true;

        // JSON 요청 여부 판단
        String accept = request.getHeader("Accept");
        String contentType = request.getContentType();
        boolean wantsJson =
                (accept != null && accept.contains("application/json")) ||
                        (contentType != null && contentType.contains("application/json")) ||
                        "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));

        if (wantsJson) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\":\"UNAUTHORIZED\",\"message\":\"login required\"}");
            return false;
        } else {
            // 로그인 페이지로 리다이렉트 (/users/login) + next 파라미터
            String uri = request.getRequestURI();
            String qs = request.getQueryString();
            String next = uri + (qs != null ? "?" + qs : "");
            String redirect = "/users/login?next=" + URLEncoder.encode(next, StandardCharsets.UTF_8);
            response.sendRedirect(redirect);
            return false;
        }
    }
}
