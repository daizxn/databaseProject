package org.example.database.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.database.entity.Users;
import org.example.database.entity.LoginResult;
import org.example.database.service.UsersService;
import org.example.database.mapper.UsersMapper;
import org.example.database.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author daizxn
* @description 针对表【daizx_users】的数据库操作Service实现
* @createDate 2025-07-04 00:00:00
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

    @Autowired
    private JwtUtil jwtUtil;

    // ========== 基本CRUD操作实现 ==========

    @Override
    public boolean addUsers(Users users) {
        return baseMapper.insertUsers(users) > 0;
    }

    @Override
    public boolean deleteUsersById(Integer id) {
        return baseMapper.deleteUsersById(id) > 0;
    }

    @Override
    public boolean deleteUsersByUsername(String username) {
        return baseMapper.deleteUsersByUsername(username) > 0;
    }

    @Override
    public boolean deleteUsersBatch(List<Integer> ids) {
        return baseMapper.deleteUsersBatch(ids) > 0;
    }

    @Override
    public boolean updateUsers(Users users) {
        return baseMapper.updateUsers(users) > 0;
    }

    @Override
    public boolean updateUsersBatch(List<Users> usersList) {
        int count = 0;
        for (Users users : usersList) {
            count += baseMapper.updateUsers(users);
        }
        return count == usersList.size();
    }

    @Override
    public Users selectUsersById(Integer id) {
        return baseMapper.selectUsersById(id);
    }

    @Override
    public Users selectUsersByUsername(String username) {
        return baseMapper.selectUsersByUsername(username);
    }

    @Override
    public List<Users> selectAllUsers() {
        return baseMapper.selectAllUsers();
    }

    @Override
    public List<Users> selectUsersByRoles(String roles) {
        return baseMapper.selectUsersByRoles(roles);
    }

    @Override
    public List<Users> selectUsersByEnable(Boolean enable) {
        return baseMapper.selectUsersByEnable(enable);
    }

    @Override
    public IPage<Users> selectUsersByPage(IPage<Users> page, Users users) {
        return baseMapper.selectUsersByPage(page, users);
    }

    @Override
    public Users login(String username, String password) {
        return baseMapper.login(username, password);
    }

    @Override
    public LoginResult loginWithToken(String username, String password) {
        try {
            // 验证用户名和密码
            Users user = baseMapper.login(username, password);

            if (user == null) {
                return LoginResult.failure("用户名或密码错误");
            }

            // 检查用户是否被禁用
            if (!user.getEnable()) {
                return LoginResult.failure("用户账户已被禁用");
            }

            // 生成JWT Token
            String token = jwtUtil.generateToken(user.getRefId(), user.getRoles(), user.getUsername());

            // 创建返回的用户信息（不包含密码）
            Users userInfo = new Users();
            userInfo.setId(user.getId());
            userInfo.setUsername(user.getUsername());
            userInfo.setRefId(user.getRefId());
            userInfo.setEnable(user.getEnable());
            userInfo.setRoles(user.getRoles());
            // 不设置password字段

            return LoginResult.success(token, userInfo);

        } catch (Exception e) {
            return LoginResult.failure("登录过程中发生错误：" + e.getMessage());
        }
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        return baseMapper.changePassword(username, oldPassword, newPassword) > 0;
    }

    @Override
    public boolean toggleUserStatus(Integer id, Boolean enable) {
        return baseMapper.toggleUserStatus(id, enable) > 0;
    }

}
