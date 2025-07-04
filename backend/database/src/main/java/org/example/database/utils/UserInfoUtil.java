package org.example.database.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 用户信息工具类
 * 用于从当前请求中获取JWT拦截器设置的用户信息
 */
public class UserInfoUtil {

    /**
     * 获取当前请求
     */
    private static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    /**
     * 获取当前用户名
     */
    public static String getCurrentUsername() {
        HttpServletRequest request = getCurrentRequest();
        return request != null ? (String) request.getAttribute("username") : null;
    }

    /**
     * 获取当前用户的refId
     */
    public static String getCurrentRefId() {
        HttpServletRequest request = getCurrentRequest();
        return request != null ? (String) request.getAttribute("refId") : null;
    }

    /**
     * 获取当前用户的角色
     */
    public static String getCurrentRoles() {
        HttpServletRequest request = getCurrentRequest();
        return request != null ? (String) request.getAttribute("roles") : null;
    }

    /**
     * 检查当前用户是否有指定角色
     */
    public static boolean hasRole(String role) {
        String roles = getCurrentRoles();
        return roles != null && roles.contains(role);
    }

    /**
     * 检查当前用户是否为管理员
     */
    public static boolean isAdmin() {
        return hasRole("admin");
    }
}
