package org.example.database.service;

import org.example.database.entity.StudentCourseTeacherScores;
import org.example.database.entity.ScoreStatistics;
import org.example.database.entity.StudentYearlyRanking;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
* @author daizxn
* @description 针对表【view_student_course_teacher_scores】的数据库操作Service
* @createDate 2025-07-05 01:29:26
*/
public interface StudentCourseTeacherScoresService extends IService<StudentCourseTeacherScores> {

    /**
     * 获取学生成绩统计信息（包含平均绩点和排名）
     * @param studentNumber 学生学号
     * @param academicYear 学年
     * @return 成绩统计信息
     */
    ScoreStatistics getScoreStatistics(String studentNumber, String academicYear);

    /**
     * 获取指定学生在指定学年的成绩统计和排名信息
     * @param studentNumber 学生学号
     * @param academicYear 学年
     * @return 学年成绩统���和排名信息
     */
    StudentYearlyRanking getStudentYearlyRanking(String studentNumber, String academicYear);

    /**
     * 获取指定学年的所有学生成绩统计和排名信息
     * @param academicYear 学年
     * @return 所有学生的学年成绩统计和排名信息列表
     */
    List<StudentYearlyRanking> getAllStudentsYearlyRanking(String academicYear);
}
