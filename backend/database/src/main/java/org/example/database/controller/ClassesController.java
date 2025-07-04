package org.example.database.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.database.common.Result;
import org.example.database.common.enums.ResultCodeEnum;
import org.example.database.entity.Classes;
import org.example.database.entity.ClassesTreeDTO;
import org.example.database.entity.Departments;
import org.example.database.service.ClassesService;
import org.example.database.service.DepartmentsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/classes")
public class ClassesController {
    @Resource
    private ClassesService classesService;

    @Resource
    private DepartmentsService departmentsService;

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
        if (classes.getDepartmentId() == null || classes.getClassName() == null) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        // Cname不重复
        LambdaQueryWrapper<Classes> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Classes::getClassName, classes.getClassName())
                .eq(Classes::getDepartmentId, classes.getDepartmentId());
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

    @GetMapping("selectByDepartmentId/{departmentId}")
    @ResponseBody
    public Result selectByDepartmentId(@PathVariable Integer departmentId) {
        if (departmentId == -1) {
            return selectAll();
        }
        LambdaQueryWrapper<Classes> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Classes::getDepartmentId, departmentId);
        List<Classes> classesList = classesService.list(queryWrapper);

        return !classesList.isEmpty() ? Result.success(classesList) : Result.error(ResultCodeEnum.NO_GOODS);
    }

    @GetMapping("/selectAll")
    @ResponseBody
    public Result selectAll() {
        List<Classes> classesList = classesService.list();
        return classesList.isEmpty() ? Result.error(ResultCodeEnum.NO_GOODS) : Result.success(classesList);
    }

    @GetMapping("/selectClassesListGroupByDepartment")
    @ResponseBody
    public Result selectClassesList() {
        // 获取所有专业
        List<Departments> departmentsList = departmentsService.list();

        // 获取所有班级
        List<Classes> classesList = classesService.list();

        // 按专业分组班级
        Map<Integer, List<Classes>> classesGroupByDepartment = classesList.stream()
                .collect(Collectors.groupingBy(Classes::getDepartmentId));

        // 构建二级分类结构
        List<ClassesTreeDTO> treeList = departmentsList.stream()
                .map(department -> {
                    // 创建专业节点
                    ClassesTreeDTO departmentNode = new ClassesTreeDTO(
                            department.getDepartmentId().toString(),
                            department.getDepartmentName()
                    );

                    // 获取该专业下的班级并转换为子节点
                    List<Classes> departmentClasses = classesGroupByDepartment.get(department.getDepartmentId());
                    if (departmentClasses != null && !departmentClasses.isEmpty()) {
                        List<ClassesTreeDTO> children = departmentClasses.stream()
                                .map(clazz -> new ClassesTreeDTO(
                                        clazz.getClassId().toString(),
                                        clazz.getClassName()
                                ))
                                .collect(Collectors.toList());
                        departmentNode.setChildren(children);
                    }

                    return departmentNode;
                })
                .collect(Collectors.toList());

        return !treeList.isEmpty() ? Result.success(treeList) : Result.error(ResultCodeEnum.NO_GOODS);
    }

    @GetMapping("/selectByName/{name}")
    @ResponseBody
    public Result selectByName(@PathVariable String name) {
        QueryWrapper<Classes> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("dzx_class_name", name);
        List<Classes> classesList = classesService.list(queryWrapper);
        return !classesList.isEmpty() ? Result.success(classesList) : Result.error(ResultCodeEnum.NO_GOODS);
    }

    @GetMapping("/selectByPage")
    @ResponseBody
    public Result selectByPage(@RequestBody Classes classes,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Classes> queryWrapper = new LambdaQueryWrapper<>();
        if (classes.getDepartmentId() != null) {
            queryWrapper.eq(Classes::getDepartmentId, classes.getDepartmentId());
        }
        if (classes.getClassName() != null) {
            queryWrapper.like(Classes::getClassName, classes.getClassName());
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
