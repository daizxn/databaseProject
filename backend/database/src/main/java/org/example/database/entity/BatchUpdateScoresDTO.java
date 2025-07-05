package org.example.database.entity;

import lombok.Data;
import java.util.List;

/**
 * 批量更新成绩请求DTO
 */
@Data
public class BatchUpdateScoresDTO {
    /**
     * 教师编号
     */
    private String teacherNumber;

    /**
     * 课程编号
     */
    private String courseNumber;

    /**
     * 学年
     */
    private String academicYear;

    /**
     * 学期
     */
    private Integer semester;

    /**
     * 学生成绩列表
     */
    private List<StudentScoreItem> scores;

    /**
     * 学生成绩项
     */
    @Data
    public static class StudentScoreItem {
        /**
         * 学号 - 使用下划线格式匹配数据库字段
         */
        private String student_no;

        /**
         * 成绩
         */
        private Integer score;
    }
}
