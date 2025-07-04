package org.example.database.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.database.common.Result;
import org.example.database.common.enums.ResultCodeEnum;
import org.example.database.entity.Courses;
import org.example.database.entity.CoursesDTO;
import org.example.database.entity.TeacherCourses;
import org.example.database.service.CoursesService;
import org.example.database.service.TeacherCoursesService;
import org.example.database.utils.NameChangeUtil;

import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CoursesController {
    @Resource
    private CoursesService coursesService;

    @Resource
    private TeacherCoursesService teacherCoursesService;

    /**
     * 插入
     *
     * @param courses 信息
     * @return 返回Result状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody Courses courses) {
        //name不为空
        if (courses.getCourseName() == null) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        // name不重复
        LambdaQueryWrapper<Courses> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Courses::getCourseName, courses.getCourseName());
        if (coursesService.count(queryWrapper) > 0) {
            return Result.error(ResultCodeEnum.PARAM_NAME_EXISTED);
        }
        return coursesService.save(courses) ? Result.success() : Result.error(ResultCodeEnum.INSERT_ERROR);
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
        return coursesService.removeById(Id) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
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
        return coursesService.removeByIds(Ids) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
    }

    @DeleteMapping("/deleteByNumber/{number}")
    @ResponseBody
    public Result deleteByNumber(@PathVariable String number) {
        if (number == null || number.isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        LambdaQueryWrapper<Courses> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Courses::getCourseNumber, number);
        return coursesService.remove(queryWrapper) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
    }
    /**
     * @param department 需要更新的数据
     * @return 返回Result状态
     */
    @PutMapping("/updateById")
    @ResponseBody
    public Result updateById(@RequestBody Courses department) {
        return coursesService.updateById(department) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @PutMapping("/updateBatch")
    @ResponseBody
    public Result updateBatch(@RequestBody List<Courses> courses) {
        return coursesService.updateBatchById(courses) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @PutMapping("/updateByNumber")
    @ResponseBody
    public Result updateByNumber(@RequestBody Courses courses) {
        if (courses.getCourseNumber() == null || courses.getCourseNumber().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        LambdaQueryWrapper<Courses> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Courses::getCourseNumber, courses.getCourseNumber());
        if (coursesService.count(queryWrapper) < 0) {
            return Result.error(ResultCodeEnum.PARAM_NAME_EXISTED);
        }
        return coursesService.update(courses, queryWrapper) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @GetMapping("/selectById/{Id}")
    @ResponseBody
    public Result selectById(@PathVariable Integer Id) {
        Courses department = coursesService.getById(Id);
        return department != null ? Result.success(department) : Result.error(ResultCodeEnum.SELECT_ERROR);
    }

    @GetMapping("/selectAll")
    @ResponseBody
    public Result selectAll() {
        List<Courses> coursesList = coursesService.list();
        return coursesList.isEmpty() ? Result.error(ResultCodeEnum.NO_GOODS) : Result.success(coursesList);
    }

    @GetMapping("/selectByName/{name}")
    @ResponseBody
    public Result selectByName(@PathVariable String name) {
        QueryWrapper<Courses> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("dzx_department_name", name);
        List<Courses> coursesList = coursesService.list(queryWrapper);
        return !coursesList.isEmpty() ? Result.success(coursesList) : Result.error(ResultCodeEnum.NO_GOODS);
    }

    @GetMapping("/selectByPage")
    @ResponseBody
    public Result selectByPage(Courses courses,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Courses> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Courses::getCourseName, courses.getCourseName());
        IPage<Courses> page = new Page<>(pageNum, pageSize);
        IPage<Courses> coursesPage = coursesService.page(page, queryWrapper);
        if (coursesPage.getRecords().isEmpty()) {
            return Result.error(ResultCodeEnum.NO_GOODS);
        } else {
            return Result.success(coursesPage);
        }
    }

    @GetMapping("/selectByPage/FullInfo")
    @ResponseBody
    public Result selectByPageFullInfo(TeacherCourses teacherCourses,
                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        QueryWrapper<TeacherCourses> queryWrapper = new QueryWrapper<>();
        //若对应字段不为空，则设为参数
        Field[] fields = TeacherCourses.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(teacherCourses);
                if (value != null) {
                    String fieldName = field.getName();
                    String ColumnName = "dzx_" + NameChangeUtil.camelToSnake(fieldName);
                    queryWrapper.like(ColumnName, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        queryWrapper.orderByAsc("dzx_course_number"); // 按教课程号升序排序

        IPage<TeacherCourses> page = new Page<>(pageNum, pageSize);
        IPage<TeacherCourses> teacherCoursesPage = teacherCoursesService.page(page, queryWrapper);
        return teacherCoursesPage.getRecords().isEmpty() ? Result.error(ResultCodeEnum.NO_GOODS) : Result.success(teacherCoursesPage);
    }

    @GetMapping("/selectByTeacher/{teacherNumber}")
    @ResponseBody
    public Result selectByTeacher(@PathVariable String teacherNumber) {
        if (teacherNumber == null || teacherNumber.isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        LambdaQueryWrapper<TeacherCourses> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeacherCourses::getTeacherNumber, teacherNumber);
        List<TeacherCourses> teacherCoursesList = teacherCoursesService.list(queryWrapper);
        return !teacherCoursesList.isEmpty() ? Result.success(teacherCoursesList) : Result.error(ResultCodeEnum.NO_GOODS);
    }

    @GetMapping("/selectByParam/HaveTeacher")
    @ResponseBody
    public Result selectByParamHaveTeacher(@RequestBody CoursesDTO coursesDTO) {
        QueryWrapper<TeacherCourses> queryWrapper = new QueryWrapper<>();
        //若对应字段不为空，则设为参数
        Field[] fields = CoursesDTO.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(coursesDTO);
                if (value != null) {
                    String fieldName = field.getName();
                    String ColumnName = "dzx_" + NameChangeUtil.camelToSnake(fieldName);
                    queryWrapper.eq(ColumnName, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        List<TeacherCourses> teacherCoursesList = teacherCoursesService.list(queryWrapper);
        return teacherCoursesList.isEmpty() ? Result.error(ResultCodeEnum.NO_GOODS) : Result.success(teacherCoursesList);
    }
}
