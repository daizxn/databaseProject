package org.example.database.controller;

import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import org.example.database.common.Result;
import org.example.database.common.enums.ResultCodeEnum;
import org.example.database.entity.Users;
import org.example.database.entity.LoginResult;
import org.example.database.service.UsersService;
import org.springframework.web.bind.annotation.*;

/**
 * Web登录相关功能控制器
 * 负责用户认证、登录、密码管理等Web端功能
 */
@RestController
@RequestMapping("/web")
public class WebController {

    @Resource
    private UsersService usersService;

    /**
     * 用户登录（返回JWT Token）
     * @param loginRequest 登录请求（包含用户名和密码）
     * @return 返回登录结果和JWT Token
     */
    @PostMapping("/login")
    @ResponseBody
    public Result loginWithToken(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty() ||
            loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        LoginResult loginResult = usersService.loginWithToken(loginRequest.getUsername(), loginRequest.getPassword());

        if (loginResult.isSuccess()) {
            return Result.success(loginResult);
        } else {
            return Result.error(ResultCodeEnum.LOGIN_ERROR.getCode(), loginResult.getMessage());
        }
    }

    /**
     * 用户登录（基础版本）
     * @param loginRequest 登录请求（包含用户名和密码）
     * @return 返回登录结果和用户信息
     */
    @PostMapping("/loginBasic")
    @ResponseBody
    public Result login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty() ||
            loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        Users users = usersService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (users != null) {
            // 不返回密码信息
            users.setPassword(null);
            return Result.success(users);
        } else {
            return Result.error(ResultCodeEnum.LOGIN_ERROR);
        }
    }

    /**
     * 修改用户密码
     * @param changePasswordRequest 修改密码请求
     * @return 返回Result状态
     */
    @PutMapping("/changePassword")
    @ResponseBody
    public Result changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        if (changePasswordRequest.getUsername() == null || changePasswordRequest.getUsername().isEmpty() ||
            changePasswordRequest.getOldPassword() == null || changePasswordRequest.getOldPassword().isEmpty() ||
            changePasswordRequest.getNewPassword() == null || changePasswordRequest.getNewPassword().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        boolean success = usersService.changePassword(
                changePasswordRequest.getUsername(),
                changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword()
        );

        return success ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    /**
     * 用户注册
     * @param registerRequest 注册请求
     * @return 返回Result状态
     */
    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestBody RegisterRequest registerRequest) {
        if (registerRequest.getUsername() == null || registerRequest.getUsername().isEmpty() ||
            registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        // 检查用户名是否已存在
        Users existingUser = usersService.selectUsersByUsername(registerRequest.getUsername());
        if (existingUser != null) {
            return Result.error(ResultCodeEnum.PARAM_NAME_EXISTED);
        }

        // 创建新用户
        Users newUser = new Users();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setRefId(registerRequest.getRefId());
        newUser.setRoles(registerRequest.getRoles() != null ? registerRequest.getRoles() : "user"); // 默认角色为user
        newUser.setEnable(true); // 默认启用

        boolean success = usersService.addUsers(newUser);
        return success ? Result.success() : Result.error(ResultCodeEnum.INSERT_ERROR);
    }

    /**
     * 获取当前用户信息（基于用户名）
     * @param username 用户名
     * @return 返回用户信息
     */
    @GetMapping("/userInfo/{username}")
    @ResponseBody
    public Result getUserInfo(@PathVariable String username) {
        if (username == null || username.isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        Users user = usersService.selectUsersByUsername(username);
        if (user != null) {
            // 不返回密码信息
            user.setPassword(null);
            return Result.success(user);
        } else {
            return Result.error(ResultCodeEnum.SELECT_ERROR);
        }
    }

    /**
     * 用户登出
     * @return 返回Result状态
     */
    @PostMapping("/logout")
    @ResponseBody
    public Result logout() {
        // JWT Token是无状态的，登出主要在前端删除Token
        // 这里可以添加登出日志记录等业务逻辑
        return Result.success("登出成功");
    }

    // ========== 内部类定义 ==========

    /**
     * 登录请求封装类
     */
    @Getter
    @Setter
    public static class LoginRequest {
        private String username;
        private String password;
    }

    /**
     * 修改密码请求封装类
     */
    @Getter
    @Setter
    public static class ChangePasswordRequest {
        private String username;
        private String oldPassword;
        private String newPassword;

    }

    /**
     * 注册请求封装类
     */
    @Getter
    @Setter
    public static class RegisterRequest {
        private String username;
        private String password;
        private String refId;
        private String roles;
    }
}
