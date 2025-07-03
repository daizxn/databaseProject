package org.example.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentScoresDTO {
    private String studentNumber;
    private String studentName;
    private String className;
    private String departmentName;
    private String courseNumber;
    private String courseName;
    private BigDecimal score;
    private String gradeLevel;
    private String teacherNumber;
    private String teacherName;
}
