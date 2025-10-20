package org.example.database.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.example.database.common.Result;
import org.example.database.common.enums.ResultCodeEnum;
import org.example.database.entity.BatchImportScoresDTO;
import org.example.database.entity.BatchUpdateScoresDTO;
import org.example.database.entity.Scores;
import org.example.database.entity.StudentCourseTeacherScores;
import org.example.database.entity.StudentScoresDTO;
import org.example.database.entity.StudentScoreSimpleDTO;
import org.example.database.service.ScoresService;
import org.example.database.service.StudentCourseTeacherScoresService;
import org.example.database.utils.NameChangeUtil;
import org.example.database.utils.PermissionUtil;
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

        // 权限验证：学生只能查询自己的成绩信息，其他用户必须是管理员
        if (PermissionUtil.isStudent()) {
            // 学生生只能查询自己的成绩信息
            String currentStudentNumber = PermissionUtil.getCurrentStudentNumber();
            if (currentStudentNumber == null) {
                return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
            }

            // 强制设置查询条件为当前学生的学号，确保学生只能查看自己的成绩
            studentScoreDTO.setStudentNumber(currentStudentNumber);
        } else if (!PermissionUtil.isAdmin()) {
            // 如果不是学生，必须是管理员才能使用此接口
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        // 管理员可以查询所有学生的成绩信息，无需额外限制

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

    /**
     * 根据课程号查询该课程的学生成绩（只包含学号、姓名、成绩）
     * @param courseNumber 课程编号
     * @return 返回学生成绩简化信息列表
     */
    @GetMapping("/selectByCourseNumber/{courseNumber}")
    @ResponseBody
    public Result selectByCourseNumber(@PathVariable String courseNumber) {
        if (courseNumber == null || courseNumber.isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        List<StudentScoreSimpleDTO> studentScores = scoresService.selectStudentsByCourseNumber(courseNumber);
        return !studentScores.isEmpty() ? Result.success(studentScores) : Result.error(ResultCodeEnum.NO_GOODS);
    }

    /**
     * 批量更新成绩
     * @param batchUpdateScoresDTO 批量更新成绩请求参数
     * @return 返回更新结果
     */
    @PostMapping("/batchUpdate")
    @ResponseBody
    public Result batchUpdateScores(@RequestBody BatchUpdateScoresDTO batchUpdateScoresDTO) {
        // 参数校验
        if (batchUpdateScoresDTO.getTeacherNumber() == null || batchUpdateScoresDTO.getTeacherNumber().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR.code, "教师编号不能为空");
        }
        if (batchUpdateScoresDTO.getCourseNumber() == null || batchUpdateScoresDTO.getCourseNumber().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR.code, "课程编号不能为空");
        }
        if (batchUpdateScoresDTO.getAcademicYear() == null || batchUpdateScoresDTO.getAcademicYear().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR.code, "学年不能为空");
        }
        if (batchUpdateScoresDTO.getSemester() == null) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR.code, "学期不能为空");
        }
        if (batchUpdateScoresDTO.getScores() == null || batchUpdateScoresDTO.getScores().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR.code, "成绩数据不能为空");
        }

        try {
            // 将成绩列表转换为JSON字符串
            ObjectMapper objectMapper = new ObjectMapper();
            String scoresJson = objectMapper.writeValueAsString(batchUpdateScoresDTO.getScores());

            // 调用存储过程
            List<String> result = scoresService.batchUpdateScores(
                    batchUpdateScoresDTO.getTeacherNumber(),
                    batchUpdateScoresDTO.getCourseNumber(),
                    batchUpdateScoresDTO.getAcademicYear(),
                    batchUpdateScoresDTO.getSemester(),
                    scoresJson
            );
            if(result.get(0).equals("f"))
                throw new RuntimeException("批量更新成绩失败: " + result.get(1));
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(ResultCodeEnum.UPDATE_ERROR.code, "批量更新成绩失败: " + e.getMessage());
        }
    }

    /**
     * 批量导入成绩
     * @param batchImportScoresDTO 批量导入成绩请求参数
     * @return 返回导入结果，包含成功和失败的统计信息
     */
    @PostMapping("/batchImport")
    @ResponseBody
    public Result batchImportScores(@RequestBody BatchImportScoresDTO batchImportScoresDTO) {
        // 参数校验
        if (batchImportScoresDTO.getTeacherNumber() == null || batchImportScoresDTO.getTeacherNumber().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR.code, "教师编号不能为空");
        }
        if (batchImportScoresDTO.getCourseNumber() == null || batchImportScoresDTO.getCourseNumber().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR.code, "课程编号不能为空");
        }
        if (batchImportScoresDTO.getAcademicYear() == null || batchImportScoresDTO.getAcademicYear().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR.code, "学年不能为空");
        }
        if (batchImportScoresDTO.getSemester() == null) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR.code, "学期不能为空");
        }
        if (batchImportScoresDTO.getScores() == null || batchImportScoresDTO.getScores().isEmpty()) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR.code, "成绩数据不能为空");
        }

        int successCount = 0;
        int errorCount = 0;
        StringBuilder errorMessages = new StringBuilder();

        for (BatchImportScoresDTO.StudentScoreItem scoreItem : batchImportScoresDTO.getScores()) {
            try {
                // 验证必要字段
                if (scoreItem.getStudent_no() == null || scoreItem.getStudent_no().trim().isEmpty()) {
                    errorCount++;
                    errorMessages.append("学号不能为空；");
                    continue;
                }

                if (scoreItem.getScore() == null) {
                    errorCount++;
                    errorMessages.append("学生").append(scoreItem.getStudent_no()).append("成绩不能为空；");
                    continue;
                }

                // 成绩范围验证
                if (scoreItem.getScore() < 0 || scoreItem.getScore() > 100) {
                    errorCount++;
                    errorMessages.append("学生").append(scoreItem.getStudent_no()).append("成绩范围应在0-100之间；");
                    continue;
                }

                // 检查成绩记录是否已存在
                LambdaQueryWrapper<Scores> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Scores::getStudentNumber, scoreItem.getStudent_no())
                           .eq(Scores::getCourseNumber, batchImportScoresDTO.getCourseNumber())
                           .eq(Scores::getAcademicYear, batchImportScoresDTO.getAcademicYear())
                           .eq(Scores::getSemester, batchImportScoresDTO.getSemester());

                // 创建成绩记录
                Scores score = new Scores();
                score.setStudentNumber(scoreItem.getStudent_no());
                score.setCourseNumber(batchImportScoresDTO.getCourseNumber());
                score.setAcademicYear(batchImportScoresDTO.getAcademicYear());
                score.setSemester(batchImportScoresDTO.getSemester());
                score.setScore(java.math.BigDecimal.valueOf(scoreItem.getScore()));

                // 如果记录已存在，则更新；否则插入新记录
                if (scoresService.count(queryWrapper) > 0) {
                    // 更新现有记录
                    LambdaUpdateWrapper<Scores> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(Scores::getStudentNumber, scoreItem.getStudent_no())
                               .eq(Scores::getCourseNumber, batchImportScoresDTO.getCourseNumber())
                               .eq(Scores::getAcademicYear, batchImportScoresDTO.getAcademicYear())
                               .eq(Scores::getSemester, batchImportScoresDTO.getSemester())
                               .set(Scores::getScore, java.math.BigDecimal.valueOf(scoreItem.getScore()));

                    if (scoresService.update(updateWrapper)) {
                        successCount++;
                    } else {
                        errorCount++;
                        errorMessages.append("学生").append(scoreItem.getStudent_no()).append("成绩更新失败；");
                    }
                } else {
                    // 插入新记录
                    if (scoresService.save(score)) {
                        successCount++;
                    } else {
                        errorCount++;
                        errorMessages.append("学生").append(scoreItem.getStudent_no()).append("成绩保存失败；");
                    }
                }

            } catch (Exception e) {
                errorCount++;
                errorMessages.append("学生").append(scoreItem.getStudent_no() != null ? scoreItem.getStudent_no() : "NULL")
                           .append("数据处理异常：").append(e.getMessage()).append("；");
            }
        }

        // 构建返回结果
        String message = String.format("批量导入成绩完成：成功%d条，失败%d条。%s",
                                      successCount, errorCount,
                                      !errorMessages.isEmpty() ? errorMessages.toString() : "");

        // 如果全部成功，返回成功状态
        if (errorCount == 0) {
            return Result.success(message);
        }
        // 如果部分成功，返回警告信息
        else if (successCount > 0) {
            return Result.error("2001", message);
        }
        // 如果全部失败，返回错误状态
        else {
            return Result.error("5031", message);
        }
    }
}
