package org.example.database.common.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.database.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT拦截器
 * 用于验证API请求中的JWT Token
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 处理跨域预检请求
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // 获取请求路径
        String requestURI = request.getRequestURI();

        // 定义不需要token验证的路径（白名单）
        if (isWhiteListPath(requestURI)) {
            return true;
        }

        // 从请求头中获取token
        String token = getTokenFromRequest(request);

        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"未提供访问令牌\",\"data\":null}");
            return false;
        }

        try {
            // 验证token
            if (!jwtUtil.validateToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"message\":\"访问令牌无效或已过期\",\"data\":null}");
                return false;
            }

            // 将用户信息存入请求属性中，供后续使用
            JwtUtil.TokenInfo tokenInfo = jwtUtil.extractTokenInfo(token);
            request.setAttribute("username", tokenInfo.getUsername());
            request.setAttribute("refId", tokenInfo.getRefId());
            request.setAttribute("roles", tokenInfo.getRoles());

            return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"访问令牌验证失败\",\"data\":null}");
            return false;
        }
    }

    /**
     * 从请求中获取Token
     * 支持从Header中的Authorization字段获取
     * 格式：Authorization: Bearer <token>
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        // 从Authorization header获取
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // 去掉"Bearer "前缀
        }

        // 也可以从自定义header获取
        String token = request.getHeader("token");
        if (token != null && !token.isEmpty()) {
            return token;
        }

        // 从请求参数获取（不推荐，但提供兼容性）
        return request.getParameter("token");
    }

    /**
     * 判断是否为白名单路径
     * 这些路径不需要token验证
     */
    private boolean isWhiteListPath(String path) {
        // 登录相关接口不需要验证
        if (path.startsWith("/web/login") ||
            path.startsWith("/web/register") ||
            path.equals("/web/logout")) {
            return true;
        }

        // 静态资源不需要验证
        if (path.startsWith("/static/") ||
            path.startsWith("/css/") ||
            path.startsWith("/js/") ||
            path.startsWith("/images/")) {
            return true;
        }

        // Swagger文档接口不需要验证（如果使用了Swagger）
        if (path.startsWith("/swagger-") ||
            path.startsWith("/v3/api-docs") ||
            path.startsWith("/webjars/")) {
            return true;
        }

        // 健康检查接口不需要验证
        if (path.startsWith("/actuator/")) {
            return true;
        }

        // 错误页面不需要验证
        if (path.startsWith("/error")) {
            return true;
        }

        return false;
    }
}
