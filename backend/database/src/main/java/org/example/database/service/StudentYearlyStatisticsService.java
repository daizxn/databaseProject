package org.example.database.service;

import org.example.database.entity.StudentYearlyStatistics;
import org.example.database.entity.StudentYearlyRanking;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @author daizxn
 * @description 针对表【view_student_yearly_statistics】的数据库操作Service
 * @createDate 2025-07-05
 */
public interface StudentYearlyStatisticsService extends IService<StudentYearlyStatistics> {

    /**
     * 获取指定学生在指定学年的成绩统计和专业排名信息
     * @param studentNumber 学生学号
     * @param academicYear 学年
     * @return 学年成绩统计和排名信息
     */
    StudentYearlyRanking getStudentYearlyRankingByDepartment(String studentNumber, String academicYear);

    /**
     * 获取指定学年指定专业的所有学生成绩统计和排名信息
     * @param academicYear 学年
     * @param departmentName 专业名称
     * @return 专业内所有学生的学年成绩统计和排名信息列表
     */
    List<StudentYearlyRanking> getDepartmentStudentsYearlyRanking(String academicYear, String departmentName);

    /**
     * 获取指定学年所有学生的成绩统计和专业排名信息
     * @param academicYear 学年
     * @return 所有学生的学年成绩统计和排名信息列表
     */
    List<StudentYearlyRanking> getAllStudentsYearlyRankingByDepartment(String academicYear);
}
