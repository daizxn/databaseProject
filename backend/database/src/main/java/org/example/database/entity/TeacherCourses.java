package org.example.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName view_teacher_courses
 */
@TableName(value ="view_teacher_courses")
@Data
public class TeacherCourses {
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
    @TableField(value = "dzx_teacher_gender")
    private String teacherGender;

    /**
     * 
     */
    @TableField(value = "dzx_teacher_title")
    private String teacherTitle;

    /**
     * 
     */
    @TableField(value = "dzx_teacher_status")
    private String teacherStatus;

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
    @TableField(value = "dzx_academic_year")
    private String academicYear;

    /**
     * 
     */
    @TableField(value = "dzx_semester")
    private Integer semester;

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
    @TableField(value = "dzx_course_status")
    private String courseStatus;

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
        TeacherCourses other = (TeacherCourses) that;
        return (this.getTeacherNumber() == null ? other.getTeacherNumber() == null : this.getTeacherNumber().equals(other.getTeacherNumber()))
            && (this.getTeacherName() == null ? other.getTeacherName() == null : this.getTeacherName().equals(other.getTeacherName()))
            && (this.getTeacherGender() == null ? other.getTeacherGender() == null : this.getTeacherGender().equals(other.getTeacherGender()))
            && (this.getTeacherTitle() == null ? other.getTeacherTitle() == null : this.getTeacherTitle().equals(other.getTeacherTitle()))
            && (this.getTeacherStatus() == null ? other.getTeacherStatus() == null : this.getTeacherStatus().equals(other.getTeacherStatus()))
            && (this.getCourseNumber() == null ? other.getCourseNumber() == null : this.getCourseNumber().equals(other.getCourseNumber()))
            && (this.getCourseName() == null ? other.getCourseName() == null : this.getCourseName().equals(other.getCourseName()))
            && (this.getCourseType() == null ? other.getCourseType() == null : this.getCourseType().equals(other.getCourseType()))
            && (this.getCourseCredits() == null ? other.getCourseCredits() == null : this.getCourseCredits().equals(other.getCourseCredits()))
            && (this.getCourseHours() == null ? other.getCourseHours() == null : this.getCourseHours().equals(other.getCourseHours()))
            && (this.getAcademicYear() == null ? other.getAcademicYear() == null : this.getAcademicYear().equals(other.getAcademicYear()))
            && (this.getSemester() == null ? other.getSemester() == null : this.getSemester().equals(other.getSemester()))
            && (this.getClassName() == null ? other.getClassName() == null : this.getClassName().equals(other.getClassName()))
            && (this.getDepartmentName() == null ? other.getDepartmentName() == null : this.getDepartmentName().equals(other.getDepartmentName()))
            && (this.getCourseStatus() == null ? other.getCourseStatus() == null : this.getCourseStatus().equals(other.getCourseStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTeacherNumber() == null) ? 0 : getTeacherNumber().hashCode());
        result = prime * result + ((getTeacherName() == null) ? 0 : getTeacherName().hashCode());
        result = prime * result + ((getTeacherGender() == null) ? 0 : getTeacherGender().hashCode());
        result = prime * result + ((getTeacherTitle() == null) ? 0 : getTeacherTitle().hashCode());
        result = prime * result + ((getTeacherStatus() == null) ? 0 : getTeacherStatus().hashCode());
        result = prime * result + ((getCourseNumber() == null) ? 0 : getCourseNumber().hashCode());
        result = prime * result + ((getCourseName() == null) ? 0 : getCourseName().hashCode());
        result = prime * result + ((getCourseType() == null) ? 0 : getCourseType().hashCode());
        result = prime * result + ((getCourseCredits() == null) ? 0 : getCourseCredits().hashCode());
        result = prime * result + ((getCourseHours() == null) ? 0 : getCourseHours().hashCode());
        result = prime * result + ((getAcademicYear() == null) ? 0 : getAcademicYear().hashCode());
        result = prime * result + ((getSemester() == null) ? 0 : getSemester().hashCode());
        result = prime * result + ((getClassName() == null) ? 0 : getClassName().hashCode());
        result = prime * result + ((getDepartmentName() == null) ? 0 : getDepartmentName().hashCode());
        result = prime * result + ((getCourseStatus() == null) ? 0 : getCourseStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", teacherNumber=").append(teacherNumber);
        sb.append(", teacherName=").append(teacherName);
        sb.append(", teacherGender=").append(teacherGender);
        sb.append(", teacherTitle=").append(teacherTitle);
        sb.append(", teacherStatus=").append(teacherStatus);
        sb.append(", courseNumber=").append(courseNumber);
        sb.append(", courseName=").append(courseName);
        sb.append(", courseType=").append(courseType);
        sb.append(", courseCredits=").append(courseCredits);
        sb.append(", courseHours=").append(courseHours);
        sb.append(", academicYear=").append(academicYear);
        sb.append(", semester=").append(semester);
        sb.append(", className=").append(className);
        sb.append(", departmentName=").append(departmentName);
        sb.append(", courseStatus=").append(courseStatus);
        sb.append("]");
        return sb.toString();
    }
}