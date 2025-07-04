package org.example.database.entity;

import lombok.Data;

/**
 * 登录结果封装类
 */
@Data
public class LoginResult {
    /**
     * 是否登录成功
     */
    private boolean success;

    /**
     * JWT Token
     */
    private String token;

    /**
     * 用户信息（不包含密码）
     */
    private Users userInfo;

    /**
     * 错误信息
     */
    private String message;

    public LoginResult() {}

    public LoginResult(boolean success, String token, Users userInfo, String message) {
        this.success = success;
        this.token = token;
        this.userInfo = userInfo;
        this.message = message;
    }

    /**
     * 创建成功的登录结果
     * @param token JWT Token
     * @param userInfo 用户信息
     * @return 登录结果
     */
    public static LoginResult success(String token, Users userInfo) {
        return new LoginResult(true, token, userInfo, "登录成功");
    }

    /**
     * 创建失败的登录结果
     * @param message 错误信息
     * @return 登录结果
     */
    public static LoginResult failure(String message) {
        return new LoginResult(false, null, null, message);
    }
}
