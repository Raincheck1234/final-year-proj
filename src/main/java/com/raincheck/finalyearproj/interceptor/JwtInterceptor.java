package com.raincheck.finalyearproj.interceptor;

import com.raincheck.finalyearproj.common.UserContext;
import com.raincheck.finalyearproj.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 放行 OPTIONS 请求（跨域预检）
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // 2. 从 Header 获取 Token
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                // 3. 验证 Token
                if (jwtUtils.validateToken(token)) {
                    String email = jwtUtils.getEmailFromToken(token);
                    UserContext.setUser(email);
                    return true;
                }
            } catch (Exception e) {
                log.error("Unauthorized: Invalid or missing token.");
            }
        }

        // 4. 验证失败，返回 401
        response.setStatus(401);
        response.getWriter().write("Unauthorized: Invalid or missing token");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 关键：请求结束后必须清理，因为 Tomcat 线程池会复用线程
        UserContext.removeUser();
    }
}