package org.example.database.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.database.common.Result;
import org.example.database.common.enums.ResultCodeEnum;
import org.example.database.entity.Classes;
import org.example.database.service.ClassesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClassesController {
    @Resource
    private ClassesService classesService;

    /**
     * 插入
     *
     * @param classes 专业信息
     * @return 返回Result状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody Classes classes) {
        //Did与Cname不为空
        if (classes.getDzxDepartmentId() == null || classes.getDzxClassName() == null) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        // Cname不重复
        LambdaQueryWrapper<Classes> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Classes::getDzxClassName, classes.getDzxClassName())
                .eq(Classes::getDzxDepartmentId, classes.getDzxDepartmentId());
        if (classesService.count(queryWrapper) > 0) {
            return Result.error(ResultCodeEnum.PARAM_NAME_EXISTED);
        }
        return classesService.save(classes) ? Result.success() : Result.error(ResultCodeEnum.INSERT_ERROR);
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
        return classesService.removeById(Id) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
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
        return classesService.removeByIds(Ids) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
    }

    /**
     * @param department 需要更新的数据
     * @return 返回Result状态
     */
    @PostMapping("/updateById")
    @ResponseBody
    public Result updateById(@RequestBody Classes department) {
        return classesService.updateById(department) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @PostMapping("/updateBatch")
    @ResponseBody
    public Result updateBatch(@RequestBody List<Classes> classes) {
        return classesService.updateBatchById(classes) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @GetMapping("/selectById/{Id}")
    @ResponseBody
    public Result selectById(@PathVariable Integer Id) {
        Classes department = classesService.getById(Id);
        return department != null ? Result.success(department) : Result.error(ResultCodeEnum.SELECT_ERROR);
    }

    @GetMapping("/selectAll")
    @ResponseBody
    public Result selectAll() {
        List<Classes> classesList = classesService.list();
        return classesList.isEmpty() ? Result.error(ResultCodeEnum.NO_GOODS) : Result.success(classesList);
    }

    @GetMapping("/selectByName/{name}")
    @ResponseBody
    public Result selectByName(@PathVariable String name) {
        QueryWrapper<Classes> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("dzx_dname", name);
        List<Classes> classesList = classesService.list(queryWrapper);
        return !classesList.isEmpty() ? Result.success(classesList) : Result.error(ResultCodeEnum.NO_GOODS);
    }

    @GetMapping("/selectByPage")
    @ResponseBody
    public Result selectByPage(@RequestBody Classes classes,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Classes> queryWrapper = new LambdaQueryWrapper<>();
        if (classes.getDzxDepartmentId() != null) {
            queryWrapper.eq(Classes::getDzxDepartmentId, classes.getDzxDepartmentId());
        }
        if (classes.getDzxClassName() != null) {
            queryWrapper.like(Classes::getDzxClassName, classes.getDzxClassName());
        }

        IPage<Classes> page = new Page<>(pageNum, pageSize);
        IPage<Classes> classesPage = classesService.page(page, queryWrapper);
        if (classesPage.getRecords().isEmpty()) {
            return Result.error(ResultCodeEnum.NO_GOODS);
        } else {
            return Result.success(classesPage);
        }
    }
}
