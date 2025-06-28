package org.example.database.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.database.common.Result;
import org.example.database.common.enums.ResultCodeEnum;
import org.example.database.entity.StudentFullInfo;
import org.example.database.entity.Students;
import org.example.database.mapper.StudentsMapper;
import org.example.database.service.StudentFullInfoService;
import org.example.database.service.StudentsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsController {
    @Resource
    private StudentsService studentsService;

    @Resource
    private StudentFullInfoService studentFullInfoService;

    /**
     * 插入
     *
     * @param students 信息
     * @return 返回Result状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody Students students) {
        //name不为空
        if (students.getStudentName() == null ) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        // name不重复
        LambdaQueryWrapper<Students> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Students::getStudentName, students.getStudentName());
        if (studentsService.count(queryWrapper) > 0) {
            return Result.error(ResultCodeEnum.PARAM_NAME_EXISTED);
        }
        return studentsService.save(students) ? Result.success() : Result.error(ResultCodeEnum.INSERT_ERROR);
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
        return studentsService.removeById(Id) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
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
        return studentsService.removeByIds(Ids) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
    }

    /**
     * @param student 需要更新的数据
     * @return 返回Result状态
     */
    @PostMapping("/updateById")
    @ResponseBody
    public Result updateById(@RequestBody Students student) {
        return studentsService.updateById(student) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @PostMapping("/updateBatch")
    @ResponseBody
    public Result updateBatch(@RequestBody List<Students> students) {
        return studentsService.updateBatchById(students) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @GetMapping("/selectByNumber/{Number}")
    @ResponseBody
    public Result selectById(@PathVariable String Number) {
        Students student = studentsService.getById(Number);
        return student != null ? Result.success(student) : Result.error(ResultCodeEnum.SELECT_ERROR);
    }

    @GetMapping("selectByNumber/FullInfo/{Number}")
    @ResponseBody
    public Result selectByNumberFullInfo(@PathVariable String Number) {
        LambdaQueryWrapper<StudentFullInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentFullInfo::getStudentNumber, Number);
        StudentFullInfo studentFullInfo = studentFullInfoService.getOne(queryWrapper);
        return studentFullInfo != null ? Result.success(studentFullInfo) : Result.error(ResultCodeEnum.SELECT_ERROR);
    }

    @GetMapping("/selectAll")
    @ResponseBody
    public Result selectAll() {
        List<Students> studentsList = studentsService.list();
        return studentsList.isEmpty() ? Result.error(ResultCodeEnum.NO_GOODS) : Result.success(studentsList);
    }

    @GetMapping("/selectByName/{name}")
    @ResponseBody
    public Result selectByName(@PathVariable String name) {
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("dzx_student_name", name);
        List<Students> studentsList = studentsService.list(queryWrapper);
        return !studentsList.isEmpty() ? Result.success(studentsList) : Result.error(ResultCodeEnum.NO_GOODS);
    }

    @GetMapping("/selectByPage")
    @ResponseBody
    public Result selectByPage(@RequestBody Students students,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Students> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Students::getStudentName, students.getStudentName());
        IPage<Students> page =new Page<>(pageNum,pageSize);
        IPage<Students> studentsPage = studentsService.page(page, queryWrapper);
        if (studentsPage.getRecords().isEmpty()) {
            return Result.error(ResultCodeEnum.NO_GOODS);
        } else {
            return Result.success(studentsPage);
        }
    }
}
