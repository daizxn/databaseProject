package org.example.database.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursesDTO {

    /**
     *
     */
    private String teacherName;

    /**
     *
     */
    private String teacherGender;

    /**
     *
     */
    private String teacherTitle;


    /**
     *
     */
    private String courseName;

    /**
     *
     */
    private String courseType;

    /**
     *
     */
    private Integer courseCredits;

    /**
     *
     */
    private Integer courseHours;

    /**
     *
     */
    private String academicYear;

    /**
     *
     */
    private Integer semester;

    /**
     *
     */
    private String className;

    /**
     *
     */
    private String departmentName;

    /**
     *
     */
    private String courseStatus;
}
