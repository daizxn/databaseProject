package org.example.database.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.database.common.Result;
import org.example.database.common.enums.ResultCodeEnum;
import org.example.database.entity.Teachers;
import org.example.database.service.TeachersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeachersController {

    @Resource
    private TeachersService teachersService;

    /**
     * 插入
     *
     * @param teachers 信息
     * @return 返回Result状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody Teachers teachers) {
        //name不为空
        if (teachers.getTeacherName() == null ) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        // name不重复
        LambdaQueryWrapper<Teachers> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachers::getTeacherName, teachers.getTeacherName());
        if (teachersService.count(queryWrapper) > 0) {
            return Result.error(ResultCodeEnum.PARAM_NAME_EXISTED);
        }
        return teachersService.save(teachers) ? Result.success() : Result.error(ResultCodeEnum.INSERT_ERROR);
    }

    /**
     * @param Id 删除对象的id
     * @return 返回Result状态
     */
    @DeleteMapping("/deleteById/{Id}")
    @ResponseBody
    public Result deleteById(@PathVariable Integer Id) {
        if (Id == null) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        return teachersService.removeById(Id) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
    }

    /**
     * @param Ids 删除对象的Id列表
     * @return 返回Result状态
     */
    @DeleteMapping("/deleteBatch")
    @ResponseBody
    public Result deleteBatch(@RequestBody List<Integer> Ids) {
        if (Ids == null || Ids.isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        return teachersService.removeByIds(Ids) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
    }

    /**
     * @param department 需要更新的数据
     * @return 返回Result状态
     */
    @PostMapping("/updateById")
    @ResponseBody
    public Result updateById(@RequestBody Teachers department) {
        return teachersService.updateById(department) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @PostMapping("/updateBatch")
    @ResponseBody
    public Result updateBatch(@RequestBody List<Teachers> teachers) {
        return teachersService.updateBatchById(teachers) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @GetMapping("/selectById/{Id}")
    @ResponseBody
    public Result selectById(@PathVariable Integer Id) {
        Teachers department = teachersService.getById(Id);
        return department != null ? Result.success(department) : Result.error(ResultCodeEnum.SELECT_ERROR);
    }

    @GetMapping("/selectAll")
    @ResponseBody
    public Result selectAll() {
        List<Teachers> teachersList = teachersService.list();
        return teachersList.isEmpty() ? Result.error(ResultCodeEnum.NO_GOODS) : Result.success(teachersList);
    }

    @GetMapping("/selectByName/{name}")
    @ResponseBody
    public Result selectByName(@PathVariable String name) {
        QueryWrapper<Teachers> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("dzx_teacher_name", name);
        List<Teachers> teachersList = teachersService.list(queryWrapper);
        return !teachersList.isEmpty() ? Result.success(teachersList) : Result.error(ResultCodeEnum.NO_GOODS);
    }

    @GetMapping("/selectByPage")
    @ResponseBody
    public Result selectByPage(@RequestBody Teachers teachers,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Teachers> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Teachers::getTeacherName, teachers.getTeacherName());
        IPage<Teachers> page =new Page<>(pageNum,pageSize);
        IPage<Teachers> teachersPage = teachersService.page(page, queryWrapper);
        if (teachersPage.getRecords().isEmpty()) {
            return Result.error(ResultCodeEnum.NO_GOODS);
        } else {
            return Result.success(teachersPage);
        }
    }
}
