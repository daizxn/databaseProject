package org.example.database.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import jakarta.annotation.Resource;
import org.example.database.common.Result;
import org.example.database.common.enums.ResultCodeEnum;
import org.example.database.entity.Departments;
import org.example.database.service.DepartmentsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentsController {

    @Resource
    private DepartmentsService departmentsService;

    /**
     * 插入
     *
     * @param departments 专业信息
     * @return 返回Result状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody Departments departments) {
        return departmentsService.save(departments) ? Result.success() : Result.error(ResultCodeEnum.INSERT_ERROR);
    }

    /**
     * @param Id 删除对象的id
     * @return 返回Result状态
     */
    @DeleteMapping("/deleteById/{Id}")
    @ResponseBody
    public Result deleteById(@PathVariable Integer Id) {
        return departmentsService.removeById(Id) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
    }

    /**
     * @param Ids 删除对象的Id列表
     * @return 返回Result状态
     */
    @DeleteMapping("/deleteBatch")
    @ResponseBody
    public Result deleteBatch(@RequestBody List<Integer> Ids) {
        return departmentsService.removeByIds(Ids) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
    }

    /**
     * @param department 需要更新的数据
     * @return 返回Result状态
     */
    @PostMapping("/updateById")
    @ResponseBody
    public Result updateById(@RequestBody Departments department) {
        return departmentsService.updateById(department) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @PostMapping("/updateBatch")
    @ResponseBody
    public Result updateBatch(@RequestBody List<Departments> departments) {
        return departmentsService.updateBatchById(departments) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @GetMapping("/selectById/{Id}")
    @ResponseBody
    public Result selectById(@PathVariable Integer Id) {
        Departments department = departmentsService.getById(Id);
        return department != null ? Result.success(department) : Result.error(ResultCodeEnum.SELECT_ERROR);
    }

    @GetMapping("/selectAll")
    @ResponseBody
    public Result selectAll() {
        List<Departments> departmentsList = departmentsService.list();
        return departmentsList.isEmpty() ? Result.error(ResultCodeEnum.NO_GOODS) : Result.success(departmentsList);
    }

    @GetMapping("/selectByName/{name}")
    @ResponseBody
    public Result selectByName(@PathVariable String name) {
//        List<Departments> departmentsList = departmentsService.selectByName(name);
//        return !departmentsList.isEmpty() ? Result.success(departmentsList) : Result.error(ResultCodeEnum.NO_GOODS);

        QueryWrapper<Departments> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("dzx_dname", name);
        List<Departments> departmentsList = departmentsService.list(queryWrapper);
        return !departmentsList.isEmpty() ? Result.success(departmentsList) : Result.error(ResultCodeEnum.NO_GOODS);
    }

    @GetMapping("/selectByPage")
    @ResponseBody
    public Result selectByPage(@RequestBody Departments departments,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper <Departments> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Departments::getDzxDname, departments.getDzxDname());
        IPage<Departments> page =new Page<>(pageNum,pageSize);
        IPage<Departments> departmentsPage = departmentsService.page(page, queryWrapper);
        if (departmentsPage.getRecords().isEmpty()) {
            return Result.error(ResultCodeEnum.NO_GOODS);
        } else {
            return Result.success(departmentsPage);
        }
    }
}
