package org.example.database.entity;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 学生成绩统计信息
 */
@Data
public class ScoreStatistics {
    /**
     * 学生学号
     */
    private String studentNumber;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生性别
     */
    private String studentGender;

    /**
     * 学生年龄
     */
    private Integer studentAge;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 学院名称
     */
    private String departmentName;

    /**
     * 地区名称
     */
    private String regionName;

    /**
     * 平均绩点 (GPA)
     */
    private BigDecimal averageGpa;

    /**
     * 排名
     */
    private Integer ranking;

    /**
     * 学年
     */
    private String academicYear;

    /**
     * 总学分
     */
    private Integer totalCredits;

    /**
     * 已获得学分
     */
    private Integer earnedCredits;
}
