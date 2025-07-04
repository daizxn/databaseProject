package org.example.database.entity;

import lombok.Data;

/**
 * 根据课程号查询学生成绩的简化DTO
 */
@Data
public class StudentScoreSimpleDTO {
    /**
     * 学生学号
     */
    private String studentNumber;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生成绩
     */
    private Integer score;

    public StudentScoreSimpleDTO() {}

    public StudentScoreSimpleDTO(String studentNumber, String studentName, Integer score) {
        this.studentNumber = studentNumber;
        this.studentName = studentName;
        this.score = score;
    }
}
