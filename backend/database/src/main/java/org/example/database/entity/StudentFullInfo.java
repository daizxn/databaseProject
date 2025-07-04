package org.example.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import lombok.Data;

/**
 * 
 * @TableName view_student_full_info
 */
@TableName(value ="view_student_full_info")
@Data
public class StudentFullInfo {
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
    @TableField(value = "dzx_student_total_credits")
    private Integer studentTotalCredits;

    /**
     * 
     */
    @TableField(value = "dzx_enrollment_date")
    private LocalDate enrollmentDate;

    /**
     * 
     */
    @TableField(value = "dzx_student_status")
    private String studentStatus;

    /**
     * 
     */
    @TableField(value = "dzx_region_name")
    private String regionName;

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
    @TableField(value = "gpa")
    private Integer gpa;

    /**
     * 
     */
    @TableField(value = "weighted_gpa")
    private Float weightedGpa;

    /**
     * 
     */
    @TableField(value = "department_gpa_rank")
    private Long departmentGpaRank;

    /**
     * 
     */
    @TableField(value = "department_total_students")
    private Long departmentTotalStudents;

    /**
     * 
     */
    @TableField(value = "department_rank_percentage")
    private Integer departmentRankPercentage;

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
        StudentFullInfo other = (StudentFullInfo) that;
        return (this.getStudentNumber() == null ? other.getStudentNumber() == null : this.getStudentNumber().equals(other.getStudentNumber()))
            && (this.getStudentName() == null ? other.getStudentName() == null : this.getStudentName().equals(other.getStudentName()))
            && (this.getStudentGender() == null ? other.getStudentGender() == null : this.getStudentGender().equals(other.getStudentGender()))
            && (this.getStudentAge() == null ? other.getStudentAge() == null : this.getStudentAge().equals(other.getStudentAge()))
            && (this.getStudentTotalCredits() == null ? other.getStudentTotalCredits() == null : this.getStudentTotalCredits().equals(other.getStudentTotalCredits()))
            && (this.getEnrollmentDate() == null ? other.getEnrollmentDate() == null : this.getEnrollmentDate().equals(other.getEnrollmentDate()))
            && (this.getStudentStatus() == null ? other.getStudentStatus() == null : this.getStudentStatus().equals(other.getStudentStatus()))
            && (this.getRegionName() == null ? other.getRegionName() == null : this.getRegionName().equals(other.getRegionName()))
            && (this.getClassName() == null ? other.getClassName() == null : this.getClassName().equals(other.getClassName()))
            && (this.getDepartmentName() == null ? other.getDepartmentName() == null : this.getDepartmentName().equals(other.getDepartmentName()))
            && (this.getGpa() == null ? other.getGpa() == null : this.getGpa().equals(other.getGpa()))
            && (this.getWeightedGpa() == null ? other.getWeightedGpa() == null : this.getWeightedGpa().equals(other.getWeightedGpa()))
            && (this.getDepartmentGpaRank() == null ? other.getDepartmentGpaRank() == null : this.getDepartmentGpaRank().equals(other.getDepartmentGpaRank()))
            && (this.getDepartmentTotalStudents() == null ? other.getDepartmentTotalStudents() == null : this.getDepartmentTotalStudents().equals(other.getDepartmentTotalStudents()))
            && (this.getDepartmentRankPercentage() == null ? other.getDepartmentRankPercentage() == null : this.getDepartmentRankPercentage().equals(other.getDepartmentRankPercentage()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStudentNumber() == null) ? 0 : getStudentNumber().hashCode());
        result = prime * result + ((getStudentName() == null) ? 0 : getStudentName().hashCode());
        result = prime * result + ((getStudentGender() == null) ? 0 : getStudentGender().hashCode());
        result = prime * result + ((getStudentAge() == null) ? 0 : getStudentAge().hashCode());
        result = prime * result + ((getStudentTotalCredits() == null) ? 0 : getStudentTotalCredits().hashCode());
        result = prime * result + ((getEnrollmentDate() == null) ? 0 : getEnrollmentDate().hashCode());
        result = prime * result + ((getStudentStatus() == null) ? 0 : getStudentStatus().hashCode());
        result = prime * result + ((getRegionName() == null) ? 0 : getRegionName().hashCode());
        result = prime * result + ((getClassName() == null) ? 0 : getClassName().hashCode());
        result = prime * result + ((getDepartmentName() == null) ? 0 : getDepartmentName().hashCode());
        result = prime * result + ((getGpa() == null) ? 0 : getGpa().hashCode());
        result = prime * result + ((getWeightedGpa() == null) ? 0 : getWeightedGpa().hashCode());
        result = prime * result + ((getDepartmentGpaRank() == null) ? 0 : getDepartmentGpaRank().hashCode());
        result = prime * result + ((getDepartmentTotalStudents() == null) ? 0 : getDepartmentTotalStudents().hashCode());
        result = prime * result + ((getDepartmentRankPercentage() == null) ? 0 : getDepartmentRankPercentage().hashCode());
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
        sb.append(", studentTotalCredits=").append(studentTotalCredits);
        sb.append(", enrollmentDate=").append(enrollmentDate);
        sb.append(", studentStatus=").append(studentStatus);
        sb.append(", regionName=").append(regionName);
        sb.append(", className=").append(className);
        sb.append(", departmentName=").append(departmentName);
        sb.append(", gpa=").append(gpa);
        sb.append(", weightedGpa=").append(weightedGpa);
        sb.append(", departmentGpaRank=").append(departmentGpaRank);
        sb.append(", departmentTotalStudents=").append(departmentTotalStudents);
        sb.append(", departmentRankPercentage=").append(departmentRankPercentage);
        sb.append("]");
        return sb.toString();
    }
}