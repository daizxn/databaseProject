package org.example.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName daizx_courses
 */
@TableName(value ="daizx_courses")
@Data
public class Courses {
    /**
     * 
     */
    @TableId(value = "dzx_course_number")
    private String courseNumber;

    /**
     * 
     */
    @TableField(value = "dzx_course_name")
    private String courseName;

    /**
     * 
     */
    @TableField(value = "dzx_teacher_number")
    private String teacherNumber;

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
    @TableField(value = "dzx_course_hours")
    private Integer courseHours;

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
    @TableField(value = "dzx_class_id")
    private Integer classId;

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
        Courses other = (Courses) that;
        return (this.getCourseNumber() == null ? other.getCourseNumber() == null : this.getCourseNumber().equals(other.getCourseNumber()))
            && (this.getCourseName() == null ? other.getCourseName() == null : this.getCourseName().equals(other.getCourseName()))
            && (this.getTeacherNumber() == null ? other.getTeacherNumber() == null : this.getTeacherNumber().equals(other.getTeacherNumber()))
            && (this.getAcademicYear() == null ? other.getAcademicYear() == null : this.getAcademicYear().equals(other.getAcademicYear()))
            && (this.getSemester() == null ? other.getSemester() == null : this.getSemester().equals(other.getSemester()))
            && (this.getCourseHours() == null ? other.getCourseHours() == null : this.getCourseHours().equals(other.getCourseHours()))
            && (this.getCourseType() == null ? other.getCourseType() == null : this.getCourseType().equals(other.getCourseType()))
            && (this.getCourseCredits() == null ? other.getCourseCredits() == null : this.getCourseCredits().equals(other.getCourseCredits()))
            && (this.getClassId() == null ? other.getClassId() == null : this.getClassId().equals(other.getClassId()))
            && (this.getCourseStatus() == null ? other.getCourseStatus() == null : this.getCourseStatus().equals(other.getCourseStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCourseNumber() == null) ? 0 : getCourseNumber().hashCode());
        result = prime * result + ((getCourseName() == null) ? 0 : getCourseName().hashCode());
        result = prime * result + ((getTeacherNumber() == null) ? 0 : getTeacherNumber().hashCode());
        result = prime * result + ((getAcademicYear() == null) ? 0 : getAcademicYear().hashCode());
        result = prime * result + ((getSemester() == null) ? 0 : getSemester().hashCode());
        result = prime * result + ((getCourseHours() == null) ? 0 : getCourseHours().hashCode());
        result = prime * result + ((getCourseType() == null) ? 0 : getCourseType().hashCode());
        result = prime * result + ((getCourseCredits() == null) ? 0 : getCourseCredits().hashCode());
        result = prime * result + ((getClassId() == null) ? 0 : getClassId().hashCode());
        result = prime * result + ((getCourseStatus() == null) ? 0 : getCourseStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", courseNumber=").append(courseNumber);
        sb.append(", courseName=").append(courseName);
        sb.append(", teacherNumber=").append(teacherNumber);
        sb.append(", academicYear=").append(academicYear);
        sb.append(", semester=").append(semester);
        sb.append(", courseHours=").append(courseHours);
        sb.append(", courseType=").append(courseType);
        sb.append(", courseCredits=").append(courseCredits);
        sb.append(", classId=").append(classId);
        sb.append(", courseStatus=").append(courseStatus);
        sb.append("]");
        return sb.toString();
    }
}