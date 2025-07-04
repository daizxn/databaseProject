package org.example.database.service;

import org.example.database.entity.StudentCourseTeacherScores;
import org.example.database.entity.ScoreStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
