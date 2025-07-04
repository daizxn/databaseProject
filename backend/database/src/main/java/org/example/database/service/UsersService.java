package org.example.database.service;

import org.example.database.entity.Users;
import org.example.database.entity.LoginResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
* @author daizxn
* @description 针对表【daizx_users】的数据库操作Service
* @createDate 2025-07-04 00:00:00
*/
public interface UsersService extends IService<Users> {

    // ========== 基本CRUD操作 ==========

    /**
     * 新增用户
     * @param users 用户信息
     * @return 是否成功
     */
    boolean addUsers(Users users);

    /**
     * 根据ID删除用户
     * @param id 用户ID
     * @return 是否成功
     */
    boolean deleteUsersById(Integer id);

    /**
     * 根据用户名删除用户
     * @param username 用户名
     * @return 是否成功
     */
    boolean deleteUsersByUsername(String username);

    /**
     * 批量删除用户
     * @param ids 用户ID列表
     * @return 是否成功
     */
    boolean deleteUsersBatch(List<Integer> ids);

    /**
     * 更新用户信息
     * @param users 用户信息
     * @return 是否成功
     */
    boolean updateUsers(Users users);

    /**
     * 批量更新用户信息
     * @param usersList 用户信息列表
     * @return 是否成功
     */
    boolean updateUsersBatch(List<Users> usersList);

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户信息
     */
    Users selectUsersById(Integer id);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    Users selectUsersByUsername(String username);

    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<Users> selectAllUsers();

    /**
     * 根据角色查询用户
     * @param roles 角色
     * @return 用户列表
     */
    List<Users> selectUsersByRoles(String roles);

    /**
     * 根据启用状态查询用户
     * @param enable 启用状态
     * @return 用户列表
     */
    List<Users> selectUsersByEnable(Boolean enable);

    /**
     * 分页查询用户信息
     * @param page 分页对象
     * @param users 查询条件
     * @return 用户分页结果
     */
    IPage<Users> selectUsersByPage(IPage<Users> page, Users users);

    /**
     * 用户登录验证
     * @param username 用户名
     * @param password 密码
     * @return 用户信息（验证成功）或null（验证失败）
     */
    Users login(String username, String password);

    /**
     * 用户登录验证并生成JWT Token
     * @param username 用户名
     * @param password 密码
     * @return 包含JWT Token的登录结果
     */
    LoginResult loginWithToken(String username, String password);

    /**
     * 修改用户密码
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否成功
     */
    boolean changePassword(String username, String oldPassword, String newPassword);

    /**
     * 启用/禁用用户
     * @param id 用户ID
     * @param enable 启用状态
     * @return 是否成功
     */
    boolean toggleUserStatus(Integer id, Boolean enable);

}
