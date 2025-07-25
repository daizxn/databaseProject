package org.example.database.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import org.example.database.entity.Scores;
import org.example.database.entity.StudentCourseTeacherScores;
import org.example.database.entity.StudentScoresDTO;
import org.example.database.entity.StudentScoreSimpleDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author daizxn
* @description 针对表【daizx_scores】的数据库操作Mapper
* @createDate 2025-06-28 22:16:10
* @Entity org.example.database.entity.Scores
*/
public interface ScoresMapper extends MppBaseMapper<Scores> {

    /**
     * 分页查询学生成绩详细信息（包含学生、课程、教师等关联信息）
     * @param page 分页对象
     * @param studentScoresDTO 查询条件
     * @return 学生成绩详细信息的分页结果
     */
    IPage<StudentCourseTeacherScores> selectStudentScoresByPage(IPage<StudentCourseTeacherScores> page, @Param("studentScores") StudentScoresDTO studentScoresDTO);

    /**
     * 根据课程号查询该课程的学生成绩（只包含学号、姓名、成绩）
     * @param courseNumber 课程编号
     * @return 学生成绩简化信息列表
     */
    List<StudentScoreSimpleDTO> selectStudentsByCourseNumber(@Param("courseNumber") String courseNumber);

    /**
     * 批量更新成绩 - 调用存储过程
     * @param teacherNumber 教师编号
     * @param courseNumber 课程编号
     * @param academicYear 学年
     * @param semester 学期
     * @param scoresJson 成绩数据（JSON格式）
     * @return 更新结果
     */
    List<String> batchUpdateScores(@Param("teacherNumber") String teacherNumber,
                                  @Param("courseNumber") String courseNumber,
                                  @Param("academicYear") String academicYear,
                                  @Param("semester") Integer semester,
                                  @Param("scoresJson") String scoresJson);

}
