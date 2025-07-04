package org.example.database.mapper;

import org.example.database.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author daizxn
* @description 针对表【daizx_users】的数据库操作Mapper
* @createDate 2025-07-04 00:00:00
* @Entity org.example.database.entity.Users
*/
public interface UsersMapper extends BaseMapper<Users> {

    // ========== 基本CRUD操作 ==========

    /**
     * 新增用户
     * @param users 用户信息
     * @return 影响行数
     */
    int insertUsers(Users users);

    /**
     * 根据ID删除用户
     * @param id 用户ID
     * @return 影响行数
     */
    int deleteUsersById(@Param("id") Integer id);

    /**
     * 根据用户名删除用户
     * @param username 用户名
     * @return 影响行数
     */
    int deleteUsersByUsername(@Param("username") String username);

    /**
     * 批量删除用户
     * @param ids 用户ID列表
     * @return 影响行数
     */
    int deleteUsersBatch(@Param("ids") List<Integer> ids);

    /**
     * 更新用户信息
     * @param users 用户信息
     * @return 影响行数
     */
    int updateUsers(Users users);

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户信息
     */
    Users selectUsersById(@Param("id") Integer id);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    Users selectUsersByUsername(@Param("username") String username);

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
    List<Users> selectUsersByRoles(@Param("roles") String roles);

    /**
     * 根据启用状态查询用户
     * @param enable 启用状态
     * @return 用户列表
     */
    List<Users> selectUsersByEnable(@Param("enable") Boolean enable);

    /**
     * 分页查询用户信息
     * @param page 分页对象
     * @param users 查询条件
     * @return 用户分页结果
     */
    IPage<Users> selectUsersByPage(IPage<Users> page, @Param("users") Users users);

    /**
     * 用户登录验证
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    Users login(@Param("username") String username, @Param("password") String password);

    /**
     * 修改用户密码
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 影响行数
     */
    int changePassword(@Param("username") String username, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);

    /**
     * 启用/禁用用户
     * @param id 用户ID
     * @param enable 启用状态
     * @return 影响行数
     */
    int toggleUserStatus(@Param("id") Integer id, @Param("enable") Boolean enable);

}
