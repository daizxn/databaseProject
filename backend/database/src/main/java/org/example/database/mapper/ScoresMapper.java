package org.example.database.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import org.example.database.entity.Scores;
import org.example.database.entity.StudentCourseTeacherScores;
import org.example.database.entity.StudentScoresDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

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

}
