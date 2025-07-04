package org.example.database.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.database.common.Result;
import org.example.database.common.enums.ResultCodeEnum;
import org.example.database.entity.Users;
import org.example.database.service.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Resource
    private UsersService usersService;

    /**
     * 新增用户
     * @param users 用户信息
     * @return 返回Result状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody Users users) {
        if (users.getUsername() == null || users.getUsername().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        // 检查用户名是否已存在
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUsername, users.getUsername());
        if (usersService.count(queryWrapper) > 0) {
            return Result.error(ResultCodeEnum.PARAM_NAME_EXISTED);
        }

        return usersService.addUsers(users) ? Result.success() : Result.error(ResultCodeEnum.INSERT_ERROR);
    }

    /**
     * 根据ID删除用户
     * @param id 用户ID
     * @return 返回Result状态
     */
    @DeleteMapping("/deleteById/{id}")
    @ResponseBody
    public Result deleteById(@PathVariable Integer id) {
        if (id == null) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        return usersService.deleteUsersById(id) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
    }

    /**
     * 根据用户名删除用户
     * @param username 用户名
     * @return 返回Result状态
     */
    @DeleteMapping("/deleteByUsername/{username}")
    @ResponseBody
    public Result deleteByUsername(@PathVariable String username) {
        if (username == null || username.isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        return usersService.deleteUsersByUsername(username) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
    }

    /**
     * 批量删除用户
     * @param ids 用户ID列表
     * @return 返回Result状态
     */
    @DeleteMapping("/deleteBatch")
    @ResponseBody
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        return usersService.deleteUsersBatch(ids) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
    }

    /**
     * 更新用户信息
     * @param users 用户信息
     * @return 返回Result状态
     */
    @PutMapping("/updateById")
    @ResponseBody
    public Result updateById(@RequestBody Users users) {
        if (users.getId() == null) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        return usersService.updateUsers(users) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    /**
     * 批量更新用户信息
     * @param usersList 用户信息列表
     * @return 返回Result状态
     */
    @PutMapping("/updateBatch")
    @ResponseBody
    public Result updateBatch(@RequestBody List<Users> usersList) {
        if (usersList == null || usersList.isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        return usersService.updateUsersBatch(usersList) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 返回用户信息
     */
    @GetMapping("/selectById/{id}")
    @ResponseBody
    public Result selectById(@PathVariable Integer id) {
        if (id == null) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        Users users = usersService.selectUsersById(id);
        return users != null ? Result.success(users) : Result.error(ResultCodeEnum.SELECT_ERROR);
    }

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 返回用户信息
     */
    @GetMapping("/selectByUsername/{username}")
    @ResponseBody
    public Result selectByUsername(@PathVariable String username) {
        if (username == null || username.isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        Users users = usersService.selectUsersByUsername(username);
        return users != null ? Result.success(users) : Result.error(ResultCodeEnum.SELECT_ERROR);
    }

    /**
     * 查询所有用户
     * @return 返回用户列表
     */
    @GetMapping("/selectAll")
    @ResponseBody
    public Result selectAll() {
        List<Users> usersList = usersService.selectAllUsers();
        return !usersList.isEmpty() ? Result.success(usersList) : Result.error(ResultCodeEnum.NO_GOODS);
    }

    /**
     * 根据角色查询用户
     * @param roles 角色
     * @return 返回用户列表
     */
    @GetMapping("/selectByRoles/{roles}")
    @ResponseBody
    public Result selectByRoles(@PathVariable String roles) {
        if (roles == null || roles.isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        List<Users> usersList = usersService.selectUsersByRoles(roles);
        return !usersList.isEmpty() ? Result.success(usersList) : Result.error(ResultCodeEnum.NO_GOODS);
    }

    /**
     * 根据启用状态查询用户
     * @param enable 启用状态
     * @return 返回用户列表
     */
    @GetMapping("/selectByEnable/{enable}")
    @ResponseBody
    public Result selectByEnable(@PathVariable Boolean enable) {
        if (enable == null) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        List<Users> usersList = usersService.selectUsersByEnable(enable);
        return !usersList.isEmpty() ? Result.success(usersList) : Result.error(ResultCodeEnum.NO_GOODS);
    }

    /**
     * 分页查询用户信息
     * @param users 查询条件
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 返回分页用户信息
     */
    @GetMapping("/selectByPage")
    @ResponseBody
    public Result selectByPage(Users users,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<Users> page = new Page<>(pageNum, pageSize);
        IPage<Users> usersPage = usersService.selectUsersByPage(page, users);

        if (usersPage.getRecords().isEmpty()) {
            return Result.error(ResultCodeEnum.NO_GOODS);
        } else {
            return Result.success(usersPage);
        }
    }

    /**
     * 启用/禁用用户
     * @param id 用户ID
     * @param enable 启用状态
     * @return 返回Result状态
     */
    @PutMapping("/toggleStatus/{id}/{enable}")
    @ResponseBody
    public Result toggleStatus(@PathVariable Integer id, @PathVariable Boolean enable) {
        if (id == null || enable == null) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        boolean success = usersService.toggleUserStatus(id, enable);
        return success ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

}
