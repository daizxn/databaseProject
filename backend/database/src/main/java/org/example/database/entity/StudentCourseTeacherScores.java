package org.example.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

/**
 * 
 * @TableName view_student_course_teacher_scores
 */
@TableName(value ="view_student_course_teacher_scores")
@Data
public class StudentCourseTeacherScores {
    /**
     * 
     */
    @TableField(value = "dzx_student_number")
    private String studentNumber;

    /**
     * 
     */
    @TableField(value = "dzx_student_name")
    private String studentName;

    /**
     * 
     */
    @TableField(value = "dzx_student_gender")
    private String studentGender;

    /**
     * 
     */
    @TableField(value = "dzx_student_age")
    private Integer studentAge;

    /**
     * 
     */
    @TableField(value = "dzx_class_name")
    private String className;

    /**
     * 
     */
    @TableField(value = "dzx_department_name")
    private String departmentName;

    /**
     * 
     */
    @TableField(value = "dzx_region_name")
    private String regionName;

    /**
     * 
     */
    @TableField(value = "dzx_course_number")
    private String courseNumber;

    /**
     * 
     */
    @TableField(value = "dzx_course_name")
    private String courseName;

    /**
     * 
     */
    @TableField(value = "dzx_course_type")
    private String courseType;

    /**
     * 
     */
    @TableField(value = "dzx_course_credits")
    private Integer courseCredits;

    /**
     * 
     */
    @TableField(value = "dzx_course_hours")
    private Integer courseHours;

    /**
     * 
     */
    @TableField(value = "dzx_teacher_number")
    private String teacherNumber;

    /**
     * 
     */
    @TableField(value = "dzx_teacher_name")
    private String teacherName;

    /**
     * 
     */
    @TableField(value = "dzx_teacher_title")
    private String teacherTitle;

    /**
     * 
     */
    @TableField(value = "dzx_semester")
    private Integer semester;

    /**
     * 
     */
    @TableField(value = "dzx_score")
    private BigDecimal score;

    /**
     * 
     */
    @TableField(value = "dzx_grade_level")
    private String gradeLevel;

    /**
     * 
     */
    @TableField(value = "dzx_exam_date")
    private LocalDate examDate;

    /**
     * 
     */
    @TableField(value = "dzx_score_status")
    private String scoreStatus;

    /**
     * 
     */
    @TableField(value = "dzx_is_passed")
    private String isPassed;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        StudentCourseTeacherScores other = (StudentCourseTeacherScores) that;
        return (this.getStudentNumber() == null ? other.getStudentNumber() == null : this.getStudentNumber().equals(other.getStudentNumber()))
            && (this.getStudentName() == null ? other.getStudentName() == null : this.getStudentName().equals(other.getStudentName()))
            && (this.getStudentGender() == null ? other.getStudentGender() == null : this.getStudentGender().equals(other.getStudentGender()))
            && (this.getStudentAge() == null ? other.getStudentAge() == null : this.getStudentAge().equals(other.getStudentAge()))
            && (this.getClassName() == null ? other.getClassName() == null : this.getClassName().equals(other.getClassName()))
            && (this.getDepartmentName() == null ? other.getDepartmentName() == null : this.getDepartmentName().equals(other.getDepartmentName()))
            && (this.getRegionName() == null ? other.getRegionName() == null : this.getRegionName().equals(other.getRegionName()))
            && (this.getCourseNumber() == null ? other.getCourseNumber() == null : this.getCourseNumber().equals(other.getCourseNumber()))
            && (this.getCourseName() == null ? other.getCourseName() == null : this.getCourseName().equals(other.getCourseName()))
            && (this.getCourseType() == null ? other.getCourseType() == null : this.getCourseType().equals(other.getCourseType()))
            && (this.getCourseCredits() == null ? other.getCourseCredits() == null : this.getCourseCredits().equals(other.getCourseCredits()))
            && (this.getCourseHours() == null ? other.getCourseHours() == null : this.getCourseHours().equals(other.getCourseHours()))
            && (this.getTeacherNumber() == null ? other.getTeacherNumber() == null : this.getTeacherNumber().equals(other.getTeacherNumber()))
            && (this.getTeacherName() == null ? other.getTeacherName() == null : this.getTeacherName().equals(other.getTeacherName()))
            && (this.getTeacherTitle() == null ? other.getTeacherTitle() == null : this.getTeacherTitle().equals(other.getTeacherTitle()))
            && (this.getSemester() == null ? other.getSemester() == null : this.getSemester().equals(other.getSemester()))
            && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()))
            && (this.getGradeLevel() == null ? other.getGradeLevel() == null : this.getGradeLevel().equals(other.getGradeLevel()))
            && (this.getExamDate() == null ? other.getExamDate() == null : this.getExamDate().equals(other.getExamDate()))
            && (this.getScoreStatus() == null ? other.getScoreStatus() == null : this.getScoreStatus().equals(other.getScoreStatus()))
            && (this.getIsPassed() == null ? other.getIsPassed() == null : this.getIsPassed().equals(other.getIsPassed()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStudentNumber() == null) ? 0 : getStudentNumber().hashCode());
        result = prime * result + ((getStudentName() == null) ? 0 : getStudentName().hashCode());
        result = prime * result + ((getStudentGender() == null) ? 0 : getStudentGender().hashCode());
        result = prime * result + ((getStudentAge() == null) ? 0 : getStudentAge().hashCode());
        result = prime * result + ((getClassName() == null) ? 0 : getClassName().hashCode());
        result = prime * result + ((getDepartmentName() == null) ? 0 : getDepartmentName().hashCode());
        result = prime * result + ((getRegionName() == null) ? 0 : getRegionName().hashCode());
        result = prime * result + ((getCourseNumber() == null) ? 0 : getCourseNumber().hashCode());
        result = prime * result + ((getCourseName() == null) ? 0 : getCourseName().hashCode());
        result = prime * result + ((getCourseType() == null) ? 0 : getCourseType().hashCode());
        result = prime * result + ((getCourseCredits() == null) ? 0 : getCourseCredits().hashCode());
        result = prime * result + ((getCourseHours() == null) ? 0 : getCourseHours().hashCode());
        result = prime * result + ((getTeacherNumber() == null) ? 0 : getTeacherNumber().hashCode());
        result = prime * result + ((getTeacherName() == null) ? 0 : getTeacherName().hashCode());
        result = prime * result + ((getTeacherTitle() == null) ? 0 : getTeacherTitle().hashCode());
        result = prime * result + ((getSemester() == null) ? 0 : getSemester().hashCode());
        result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
        result = prime * result + ((getGradeLevel() == null) ? 0 : getGradeLevel().hashCode());
        result = prime * result + ((getExamDate() == null) ? 0 : getExamDate().hashCode());
        result = prime * result + ((getScoreStatus() == null) ? 0 : getScoreStatus().hashCode());
        result = prime * result + ((getIsPassed() == null) ? 0 : getIsPassed().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", studentNumber=").append(studentNumber);
        sb.append(", studentName=").append(studentName);
        sb.append(", studentGender=").append(studentGender);
        sb.append(", studentAge=").append(studentAge);
        sb.append(", className=").append(className);
        sb.append(", departmentName=").append(departmentName);
        sb.append(", regionName=").append(regionName);
        sb.append(", courseNumber=").append(courseNumber);
        sb.append(", courseName=").append(courseName);
        sb.append(", courseType=").append(courseType);
        sb.append(", courseCredits=").append(courseCredits);
        sb.append(", courseHours=").append(courseHours);
        sb.append(", teacherNumber=").append(teacherNumber);
        sb.append(", teacherName=").append(teacherName);
        sb.append(", teacherTitle=").append(teacherTitle);
        sb.append(", semester=").append(semester);
        sb.append(", score=").append(score);
        sb.append(", gradeLevel=").append(gradeLevel);
        sb.append(", examDate=").append(examDate);
        sb.append(", scoreStatus=").append(scoreStatus);
        sb.append(", isPassed=").append(isPassed);
        sb.append("]");
        return sb.toString();
    }
}