package org.example.database.entity;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 学生学年成绩统计和排名信息
 */
@Data
public class StudentYearlyRanking {
    /**
     * 学生学号
     */
    private String studentNumber;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 学院名称
     */
    private String departmentName;

    /**
     * 学年
     */
    private String academicYear;

    /**
     * 平均分数
     */
    private BigDecimal averageScore;

    /**
     * 平均绩点 (按公式计算: (分数-50)*5/50)
     */
    private BigDecimal averageGpa;

    /**
     * 总学分
     */
    private Integer totalCredits;

    /**
     * 已获得学分（及格课程的学分）
     */
    private Integer earnedCredits;

    /**
     * 课程总数
     */
    private Integer totalCourses;

    /**
     * 及格课程数
     */
    private Integer passedCourses;

    /**
     * 在班级中的排名
     */
    private Integer classRanking;

    /**
     * 班级总人数
     */
    private Integer classTotalStudents;

    /**
     * 在学院中的排名
     */
    private Integer departmentRanking;

    /**
     * 学院总人数
     */
    private Integer departmentTotalStudents;
}
