package org.example.database.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.database.entity.StudentYearlyStatistics;
import org.example.database.entity.StudentYearlyRanking;
import org.example.database.service.StudentYearlyStatisticsService;
import org.example.database.mapper.StudentYearlyStatisticsMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author daizxn
 * @description 针对表【view_student_yearly_statistics】的数据库操作Service实现
 * @createDate 2025-07-05
 */
@Service
public class StudentYearlyStatisticsServiceImpl extends ServiceImpl<StudentYearlyStatisticsMapper, StudentYearlyStatistics>
    implements StudentYearlyStatisticsService {

    @Override
    public StudentYearlyRanking getStudentYearlyRankingByDepartment(String studentNumber, String academicYear) {
        // 1. 获取该学生的统计数据
        QueryWrapper<StudentYearlyStatistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dzx_student_number", studentNumber)
                   .eq("dzx_academic_year", academicYear);

        StudentYearlyStatistics studentStats = this.getOne(queryWrapper);

        if (studentStats == null) {
            return null;
        }

        // 2. 获取同专业所有学生的统计数据用于排名计算
        List<StudentYearlyRanking> departmentRankings = getDepartmentStudentsYearlyRanking(
            academicYear, studentStats.getDepartmentName());

        // 3. 找到当前学生的排名信息
        return departmentRankings.stream()
                .filter(r -> r.getStudentNumber().equals(studentNumber))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<StudentYearlyRanking> getDepartmentStudentsYearlyRanking(String academicYear, String departmentName) {
        // 获取指定学年和专业的所有学生统计数据
        QueryWrapper<StudentYearlyStatistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dzx_academic_year", academicYear)
                   .eq("dzx_department_name", departmentName)
                   .orderByDesc("dzx_average_gpa");

        List<StudentYearlyStatistics> departmentStats = this.list(queryWrapper);

        // 转换为StudentYearlyRanking并计算排名
        List<StudentYearlyRanking> rankings = new ArrayList<>();

        for (int i = 0; i < departmentStats.size(); i++) {
            StudentYearlyStatistics stats = departmentStats.get(i);
            StudentYearlyRanking ranking = convertToRanking(stats);

            // 设置专业排名
            ranking.setDepartmentRanking(i + 1);
            ranking.setDepartmentTotalStudents(departmentStats.size());

            rankings.add(ranking);
        }

        // 计算班级排名
        calculateClassRankings(rankings);

        return rankings;
    }

    @Override
    public List<StudentYearlyRanking> getAllStudentsYearlyRankingByDepartment(String academicYear) {
        // 获取指定学年的所有学生统计数据
        QueryWrapper<StudentYearlyStatistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dzx_academic_year", academicYear);

        List<StudentYearlyStatistics> allStats = this.list(queryWrapper);

        if (allStats.isEmpty()) {
            return new ArrayList<>();
        }

        // 按专业分组
        Map<String, List<StudentYearlyStatistics>> departmentGroups = allStats.stream()
                .collect(Collectors.groupingBy(StudentYearlyStatistics::getDepartmentName));

        List<StudentYearlyRanking> allRankings = new ArrayList<>();

        // 为每个专业计算排名
        for (Map.Entry<String, List<StudentYearlyStatistics>> entry : departmentGroups.entrySet()) {
            String deptName = entry.getKey();
            List<StudentYearlyStatistics> deptStats = entry.getValue();

            // 按绩点降序排序
            deptStats.sort((a, b) -> b.getAverageGpa().compareTo(a.getAverageGpa()));

            // 转换为StudentYearlyRanking并设置专业排名
            for (int i = 0; i < deptStats.size(); i++) {
                StudentYearlyStatistics stats = deptStats.get(i);
                StudentYearlyRanking ranking = convertToRanking(stats);

                // 设置专业排名
                ranking.setDepartmentRanking(i + 1);
                ranking.setDepartmentTotalStudents(deptStats.size());

                allRankings.add(ranking);
            }
        }

        // 计算班级排名
        calculateClassRankings(allRankings);

        return allRankings;
    }

    /**
     * 将StudentYearlyStatistics转换为StudentYearlyRanking
     */
    private StudentYearlyRanking convertToRanking(StudentYearlyStatistics stats) {
        StudentYearlyRanking ranking = new StudentYearlyRanking();

        ranking.setStudentNumber(stats.getStudentNumber());
        ranking.setStudentName(stats.getStudentName());
        ranking.setClassName(stats.getClassName());
        ranking.setDepartmentName(stats.getDepartmentName());
        ranking.setAcademicYear(stats.getAcademicYear());
        ranking.setAverageScore(stats.getAverageScore());
        ranking.setAverageGpa(stats.getAverageGpa());
        ranking.setTotalCredits(stats.getTotalCredits());
        ranking.setEarnedCredits(stats.getEarnedCredits());
        ranking.setTotalCourses(stats.getTotalCourses());
        ranking.setPassedCourses(stats.getPassedCourses());

        return ranking;
    }

    /**
     * 计算班级排名
     */
    private void calculateClassRankings(List<StudentYearlyRanking> rankings) {
        // 按班级分组计算班级排名
        Map<String, List<StudentYearlyRanking>> classGroups = rankings.stream()
                .collect(Collectors.groupingBy(StudentYearlyRanking::getClassName));

        for (Map.Entry<String, List<StudentYearlyRanking>> entry : classGroups.entrySet()) {
            List<StudentYearlyRanking> classStudents = entry.getValue();

            // 按绩点降序排序
            classStudents.sort((a, b) -> b.getAverageGpa().compareTo(a.getAverageGpa()));

            // 设置班级排名
            for (int i = 0; i < classStudents.size(); i++) {
                StudentYearlyRanking student = classStudents.get(i);
                student.setClassRanking(i + 1);
                student.setClassTotalStudents(classStudents.size());
            }
        }
    }
}
