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
    @TableId
    private String dzxCourseNumber;

    /**
     * 
     */
    private String dzxCourseName;

    /**
     * 
     */
    private String dzxTeacherNumber;

    /**
     * 
     */
    private String dzxAcademicYear;

    /**
     * 
     */
    private Integer dzxSemester;

    /**
     * 
     */
    private Integer dzxCourseHours;

    /**
     * 
     */
    private String dzxCourseType;

    /**
     * 
     */
    private Integer dzxCourseCredits;

    /**
     * 
     */
    private Integer dzxClassId;

    /**
     * 
     */
    private String dzxCourseStatus;

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
        return (this.getDzxCourseNumber() == null ? other.getDzxCourseNumber() == null : this.getDzxCourseNumber().equals(other.getDzxCourseNumber()))
            && (this.getDzxCourseName() == null ? other.getDzxCourseName() == null : this.getDzxCourseName().equals(other.getDzxCourseName()))
            && (this.getDzxTeacherNumber() == null ? other.getDzxTeacherNumber() == null : this.getDzxTeacherNumber().equals(other.getDzxTeacherNumber()))
            && (this.getDzxAcademicYear() == null ? other.getDzxAcademicYear() == null : this.getDzxAcademicYear().equals(other.getDzxAcademicYear()))
            && (this.getDzxSemester() == null ? other.getDzxSemester() == null : this.getDzxSemester().equals(other.getDzxSemester()))
            && (this.getDzxCourseHours() == null ? other.getDzxCourseHours() == null : this.getDzxCourseHours().equals(other.getDzxCourseHours()))
            && (this.getDzxCourseType() == null ? other.getDzxCourseType() == null : this.getDzxCourseType().equals(other.getDzxCourseType()))
            && (this.getDzxCourseCredits() == null ? other.getDzxCourseCredits() == null : this.getDzxCourseCredits().equals(other.getDzxCourseCredits()))
            && (this.getDzxClassId() == null ? other.getDzxClassId() == null : this.getDzxClassId().equals(other.getDzxClassId()))
            && (this.getDzxCourseStatus() == null ? other.getDzxCourseStatus() == null : this.getDzxCourseStatus().equals(other.getDzxCourseStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDzxCourseNumber() == null) ? 0 : getDzxCourseNumber().hashCode());
        result = prime * result + ((getDzxCourseName() == null) ? 0 : getDzxCourseName().hashCode());
        result = prime * result + ((getDzxTeacherNumber() == null) ? 0 : getDzxTeacherNumber().hashCode());
        result = prime * result + ((getDzxAcademicYear() == null) ? 0 : getDzxAcademicYear().hashCode());
        result = prime * result + ((getDzxSemester() == null) ? 0 : getDzxSemester().hashCode());
        result = prime * result + ((getDzxCourseHours() == null) ? 0 : getDzxCourseHours().hashCode());
        result = prime * result + ((getDzxCourseType() == null) ? 0 : getDzxCourseType().hashCode());
        result = prime * result + ((getDzxCourseCredits() == null) ? 0 : getDzxCourseCredits().hashCode());
        result = prime * result + ((getDzxClassId() == null) ? 0 : getDzxClassId().hashCode());
        result = prime * result + ((getDzxCourseStatus() == null) ? 0 : getDzxCourseStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dzxCourseNumber=").append(dzxCourseNumber);
        sb.append(", dzxCourseName=").append(dzxCourseName);
        sb.append(", dzxTeacherNumber=").append(dzxTeacherNumber);
        sb.append(", dzxAcademicYear=").append(dzxAcademicYear);
        sb.append(", dzxSemester=").append(dzxSemester);
        sb.append(", dzxCourseHours=").append(dzxCourseHours);
        sb.append(", dzxCourseType=").append(dzxCourseType);
        sb.append(", dzxCourseCredits=").append(dzxCourseCredits);
        sb.append(", dzxClassId=").append(dzxClassId);
        sb.append(", dzxCourseStatus=").append(dzxCourseStatus);
        sb.append("]");
        return sb.toString();
    }
}