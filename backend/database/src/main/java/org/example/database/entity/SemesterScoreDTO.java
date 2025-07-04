package org.example.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 学期成绩DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemesterScoreDTO {

    /**
     * 学期（如：1、2）
     */
    private String semester;

    /**
     * 课程编号
     */
    private String courseNumber;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程学分
     */
    private BigDecimal courseCredits;

    /**
     * 成绩
     */
    private BigDecimal score;

    /**
     * 等级（优秀、良好、中等、及格、不及格）
     */
    private String gradeLevel;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 课程类型
     */
    private String courseType;
}
