package org.example.database.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.database.common.Result;
import org.example.database.common.enums.ResultCodeEnum;
import org.example.database.entity.*;
import org.example.database.exception.CustomException;
import org.example.database.service.StudentCourseTeacherScoresService;
import org.example.database.service.StudentFullInfoService;
import org.example.database.service.StudentsService;
import org.example.database.service.StudentYearlyStatisticsService;
import org.example.database.utils.NameChangeUtil;
import org.example.database.utils.PermissionUtil;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsController {
    @Resource
    private StudentsService studentsService;

    @Resource
    private StudentFullInfoService studentFullInfoService;

    @Resource
    private StudentCourseTeacherScoresService studentCourseTeacherScoresService;

    @Resource
    private StudentYearlyStatisticsService studentYearlyStatisticsService;

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
        if (students.getStudentName() == null) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        //Number唯一
        LambdaQueryWrapper<Students> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Students::getStudentNumber, students.getStudentNumber());
        if (studentsService.count(queryWrapper) > 0) {
            return Result.error(ResultCodeEnum.ID_EXIST_ERROR);
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

    @DeleteMapping("/deleteByNumber/{number}")
    @ResponseBody
    public Result deleteByNumber(@PathVariable String number) {
        if (number == null || number.isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        LambdaQueryWrapper<Students> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Students::getStudentNumber, number);
        return studentsService.remove(queryWrapper) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
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
    @PutMapping("/updateById")
    @ResponseBody
    public Result updateById(@RequestBody Students student) {
        return studentsService.updateById(student) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @PutMapping("/updateByNumber")
    @ResponseBody
    public Result updateByNumber(@RequestBody Students student) {
        if (student.getStudentNumber() == null || student.getStudentNumber().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        LambdaQueryWrapper<Students> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Students::getStudentNumber, student.getStudentNumber());
        if (studentsService.count(queryWrapper) < 0) {
            return Result.error(ResultCodeEnum.ID_NOT_EXIST_ERROR);
        }
        return studentsService.update(student, queryWrapper) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @PutMapping("/updateBatch")
    @ResponseBody
    public Result updateBatch(@RequestBody List<Students> students) {
        return studentsService.updateBatchById(students) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @GetMapping("/selectById/{number}")
    @ResponseBody
    public Result selectById(@PathVariable String number) {
        Students student = studentsService.getById(number);
        return student != null ? Result.success(student) : Result.error(ResultCodeEnum.SELECT_ERROR);
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
        IPage<Students> page = new Page<>(pageNum, pageSize);
        IPage<Students> studentsPage = studentsService.page(page, queryWrapper);
        if (studentsPage.getRecords().isEmpty()) {
            return Result.error(ResultCodeEnum.NO_GOODS);
        } else {
            return Result.success(studentsPage);
        }
    }

    @GetMapping("/selectByPage/FullInfo")
    @ResponseBody
    public Result selectByPageFullInfo(StudentFullInfo studentDTO,
                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        QueryWrapper<StudentFullInfo> queryWrapper = new QueryWrapper<>();
        Field[] fields = StudentFullInfo.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(studentDTO);
                if (value != null) {
                    String fieldName = field.getName();
                    String ColumnName = "dzx_" + NameChangeUtil.camelToSnake(fieldName);
                    queryWrapper.like(ColumnName, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        queryWrapper.orderByAsc("dzx_student_number"); // 按学号升序��序

        IPage<StudentFullInfo> page = new Page<>(pageNum, pageSize);
        IPage<StudentFullInfo> studentsPage = studentFullInfoService.page(page, queryWrapper);

        if (studentsPage.getRecords().isEmpty()) {
            return Result.error(ResultCodeEnum.NO_GOODS);
        } else {
            return Result.success(studentsPage);
        }
    }

    /**
     * 获取学生在指定学年的成绩统计和专业排名信息
     * @param academicYear 学年（例如：2023-2024）
     * @return 包含成绩统计、绩点计算和专业内排名信息的结果
     */
    @GetMapping("/yearlyRanking")
    @ResponseBody
    public Result getStudentYearlyRanking(@RequestParam String academicYear) {
        try {
            String studentNumber;

            // 权限验证：只有学生可以查询自己的成绩统计
            if (PermissionUtil.isStudent()) {
                studentNumber = PermissionUtil.getCurrentStudentNumber();
            } else {
                throw new CustomException(ResultCodeEnum.NO_PERMISSION);
            }

            // 调用服务层获取学年成绩统计和专业排名信息
            StudentYearlyRanking ranking = studentYearlyStatisticsService.getStudentYearlyRankingByDepartment(studentNumber, academicYear);

            if (ranking == null) {
                return Result.error(ResultCodeEnum.DATA_NOT_FOUND);
            }

            return Result.success(ranking);

        } catch (CustomException e) {
            return Result.error(e.getCode(), e.getMsg());
        } catch (Exception e) {
            return Result.error(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 获取指定学年指定专业所有学生的成绩统计和排名信息（管理员接口）
     * @param academicYear 学年（例如：2023-2024）
     * @param departmentName 专业名称（可选，不传则获���所有专业）
     * @return 指定专业所有学生的成绩统计、绩点计算和专业内排名信息列表
     */
    @GetMapping("/departmentYearlyRanking")
    @ResponseBody
    public Result getDepartmentYearlyRanking(@RequestParam String academicYear,
                                           @RequestParam(required = false) String departmentName) {
        try {
            // 权限验证：只有管理员可以查询所有学生的成绩统计
            if (!PermissionUtil.isAdmin()) {
                throw new CustomException(ResultCodeEnum.NO_PERMISSION);
            }

            List<StudentYearlyRanking> rankings;

            if (departmentName != null && !departmentName.trim().isEmpty()) {
                // 获取指定专业的学生排名
                rankings = studentYearlyStatisticsService.getDepartmentStudentsYearlyRanking(academicYear, departmentName);
            } else {
                // 获取所有专业的学生排名
                rankings = studentYearlyStatisticsService.getAllStudentsYearlyRankingByDepartment(academicYear);
            }

            if (rankings.isEmpty()) {
                return Result.error(ResultCodeEnum.DATA_NOT_FOUND);
            }

            return Result.success(rankings);

        } catch (CustomException e) {
            return Result.error(e.getCode(), e.getMsg());
        } catch (Exception e) {
            return Result.error(ResultCodeEnum.SYSTEM_ERROR);
        }
    }


}
