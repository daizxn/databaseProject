package org.example.database.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.database.common.Result;
import org.example.database.common.enums.ResultCodeEnum;
import org.example.database.entity.StudentDTO;
import org.example.database.entity.Students;
import org.example.database.service.StudentFullInfoService;
import org.example.database.service.StudentsService;
import org.example.database.utils.NameChangeUtil;
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

    @GetMapping("/selectById/FullInfo/{number}")
    @ResponseBody
    public Result selectByIdFullInfo(@PathVariable String number) {
        LambdaQueryWrapper<StudentDTO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentDTO::getStudentNumber, number);
        StudentDTO studentDTO = studentFullInfoService.getOne(queryWrapper);
        return studentDTO != null ? Result.success(studentDTO) : Result.error(ResultCodeEnum.SELECT_ERROR);
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

    @GetMapping("/selectByPage/FullInfo")
    @ResponseBody
    public Result selectByPageFullInfo(StudentDTO studentDTO,
                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        QueryWrapper<StudentDTO> queryWrapper = new QueryWrapper<>();
        Field[] fields = StudentDTO.class.getDeclaredFields();
        for(Field field : fields) {
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

        queryWrapper.orderByAsc("dzx_student_number"); // 按学号升序排序

        IPage<StudentDTO> page =new Page<>(pageNum,pageSize);
        IPage<StudentDTO> studentsPage = studentFullInfoService.page(page, queryWrapper);

        if (studentsPage.getRecords().isEmpty()) {
            return Result.error(ResultCodeEnum.NO_GOODS);
        } else {
            return Result.success(studentsPage);
        }
    }
}
