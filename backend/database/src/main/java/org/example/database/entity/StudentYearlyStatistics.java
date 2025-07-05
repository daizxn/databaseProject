package org.example.database.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 学生学年统计视图实体类
 * @TableName view_student_yearly_statistics
 */
@TableName(value = "view_student_yearly_statistics")
@Data
public class StudentYearlyStatistics {
    /**
     * 学生学号
     */
    @TableField(value = "dzx_student_number")
    private String studentNumber;

    /**
     * 学生姓名
     */
    @TableField(value = "dzx_student_name")
    private String studentName;

    /**
     * 班级名称
     */
    @TableField(value = "dzx_class_name")
    private String className;

    /**
     * 学院名称
     */
    @TableField(value = "dzx_department_name")
    private String departmentName;

    /**
     * 学年
     */
    @TableField(value = "dzx_academic_year")
    private String academicYear;

    /**
     * 平均分数
     */
    @TableField(value = "dzx_average_score")
    private BigDecimal averageScore;

    /**
     * 平均绩点
     */
    @TableField(value = "dzx_average_gpa")
    private BigDecimal averageGpa;

    /**
     * 总学分
     */
    @TableField(value = "dzx_total_credits")
    private Integer totalCredits;

    /**
     * 已获得学分
     */
    @TableField(value = "dzx_earned_credits")
    private Integer earnedCredits;

    /**
     * 课程总数
     */
    @TableField(value = "dzx_total_courses")
    private Integer totalCourses;

    /**
     * 通过课程数
     */
    @TableField(value = "dzx_passed_courses")
    private Integer passedCourses;

    /**
     * 优秀课程数
     */
    @TableField(value = "dzx_excellent_courses")
    private Integer excellentCourses;
}
