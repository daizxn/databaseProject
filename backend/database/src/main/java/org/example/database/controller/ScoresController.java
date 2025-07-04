package org.example.database.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.example.database.common.Result;
import org.example.database.common.enums.ResultCodeEnum;
import org.example.database.entity.Scores;
import org.example.database.entity.StudentCourseTeacherScores;
import org.example.database.entity.StudentScoresDTO;
import org.example.database.service.ScoresService;
import org.example.database.service.StudentCourseTeacherScoresService;
import org.example.database.utils.NameChangeUtil;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

@RestController
@RequestMapping("/scores")
public class ScoresController {
    @Resource
    private ScoresService scoresService;

    @Resource
    private StudentCourseTeacherScoresService studentCourseTeacherScoresService;

    /**
     * 插入
     *
     * @param scores 信息
     * @return 返回Result状态
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody Scores scores) {
        return scoresService.save(scores) ? Result.success() : Result.error(ResultCodeEnum.INSERT_ERROR);
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
        return scoresService.removeById(Id) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
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
        return scoresService.removeByIds(Ids) ? Result.success() : Result.error(ResultCodeEnum.DELETE_ERROR);
    }

    /**
     * @param department 需要更新的数据
     * @return 返回Result状态
     */
    @PutMapping("/updateById")
    @ResponseBody
    public Result updateById(@RequestBody Scores department) {
        return scoresService.updateById(department) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @PutMapping("/updateBatch")
    @ResponseBody
    public Result updateBatch(@RequestBody List<Scores> scores) {
        return scoresService.updateBatchById(scores) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @PutMapping("/updateScoresByNumber")
    @ResponseBody
    public Result updateScoresByNumber(@RequestBody Scores scores) {
        if (scores.getStudentNumber() == null || scores.getCourseNumber() == null) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        LambdaUpdateWrapper<Scores> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Scores::getStudentNumber, scores.getStudentNumber())
                .eq(Scores::getCourseNumber, scores.getCourseNumber())
                .set(Scores::getScore, scores.getScore());
        return scoresService.update(wrapper) ? Result.success() : Result.error(ResultCodeEnum.UPDATE_ERROR);
    }

    @GetMapping("/selectById/{Id}")
    @ResponseBody
    public Result selectById(@PathVariable Integer Id) {
        Scores department = scoresService.getById(Id);
        return department != null ? Result.success(department) : Result.error(ResultCodeEnum.SELECT_ERROR);
    }

    @GetMapping("/selectAll")
    @ResponseBody
    public Result selectAll() {
        List<Scores> scoresList = scoresService.list();
        return scoresList.isEmpty() ? Result.error(ResultCodeEnum.NO_GOODS) : Result.success(scoresList);
    }


    @GetMapping("/selectByPage")
    @ResponseBody
    public Result selectByPage(Scores scores,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Scores> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Scores::getStudentNumber, scores.getStudentNumber());
        queryWrapper.eq(Scores::getTeacherNumber, scores.getTeacherNumber());
        queryWrapper.eq(Scores::getCourseNumber, scores.getCourseNumber());
        IPage<Scores> page = new Page<>(pageNum, pageSize);
        IPage<Scores> scoresPage = scoresService.page(page, queryWrapper);
        if (scoresPage.getRecords().isEmpty()) {
            return Result.error(ResultCodeEnum.NO_GOODS);
        } else {
            return Result.success(scoresPage);
        }
    }


    @GetMapping("/selectByPageWithStudent")
    @ResponseBody
    public Result selectByPageWithStudent(StudentScoresDTO studentScoreDTO,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        QueryWrapper<StudentCourseTeacherScores> queryWrapper = new QueryWrapper<>();
        Field[] fields = StudentScoresDTO.class.getDeclaredFields();
        for(Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(studentScoreDTO);

                if (value != null) {
                    String fieldName = field.getName();
                    String ColumnName = "dzx_" + NameChangeUtil.camelToSnake(fieldName);
                    queryWrapper.like(ColumnName, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        queryWrapper.orderByAsc("dzx_student_number"); // 按学生编号升序排序

        IPage<StudentCourseTeacherScores> page = new Page<>(pageNum, pageSize);
        IPage<StudentCourseTeacherScores> studentCourseTeacherScoresIPage = studentCourseTeacherScoresService.page(page, queryWrapper);

        List<StudentScoresDTO> studentScoresDTOS=  studentCourseTeacherScoresIPage.getRecords().stream().map(studentCourseTeacherScores -> new StudentScoresDTO(
                studentCourseTeacherScores.getStudentNumber(),
                studentCourseTeacherScores.getStudentName(),
                studentCourseTeacherScores.getClassName(),
                studentCourseTeacherScores.getDepartmentName(),
                studentCourseTeacherScores.getCourseNumber(),
                studentCourseTeacherScores.getCourseName(),
                studentCourseTeacherScores.getScore(),
                studentCourseTeacherScores.getGradeLevel(),
                studentCourseTeacherScores.getTeacherNumber(),
                studentCourseTeacherScores.getTeacherName()
        )).toList();

        IPage<StudentScoresDTO> studentScoresIPage = new Page<>(pageNum, pageSize);
        studentScoresIPage.setRecords(studentScoresDTOS);
        studentScoresIPage.setTotal(studentCourseTeacherScoresIPage.getTotal());

        return studentScoresIPage.getRecords().isEmpty() ?
            Result.error(ResultCodeEnum.NO_GOODS) : Result.success(studentScoresIPage);
    }
}
