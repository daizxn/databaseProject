package org.example.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import lombok.Data;

/**
 * 
 * @TableName daizx_students
 */
@TableName(value ="daizx_students")
@Data
public class Students {
    /**
     * 
     */
    @TableId(value = "dzx_student_number")
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
    @TableField(value = "dzx_region_id")
    private Integer regionId;

    /**
     * 
     */
    @TableField(value = "dzx_student_total_credits")
    private Integer studentTotalCredits;

    /**
     * 
     */
    @TableField(value = "dzx_class_id")
    private Integer classId;

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
        Students other = (Students) that;
        return (this.getStudentNumber() == null ? other.getStudentNumber() == null : this.getStudentNumber().equals(other.getStudentNumber()))
            && (this.getStudentName() == null ? other.getStudentName() == null : this.getStudentName().equals(other.getStudentName()))
            && (this.getStudentGender() == null ? other.getStudentGender() == null : this.getStudentGender().equals(other.getStudentGender()))
            && (this.getStudentAge() == null ? other.getStudentAge() == null : this.getStudentAge().equals(other.getStudentAge()))
            && (this.getRegionId() == null ? other.getRegionId() == null : this.getRegionId().equals(other.getRegionId()))
            && (this.getStudentTotalCredits() == null ? other.getStudentTotalCredits() == null : this.getStudentTotalCredits().equals(other.getStudentTotalCredits()))
            && (this.getClassId() == null ? other.getClassId() == null : this.getClassId().equals(other.getClassId()))
            && (this.getEnrollmentDate() == null ? other.getEnrollmentDate() == null : this.getEnrollmentDate().equals(other.getEnrollmentDate()))
            && (this.getStudentStatus() == null ? other.getStudentStatus() == null : this.getStudentStatus().equals(other.getStudentStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStudentNumber() == null) ? 0 : getStudentNumber().hashCode());
        result = prime * result + ((getStudentName() == null) ? 0 : getStudentName().hashCode());
        result = prime * result + ((getStudentGender() == null) ? 0 : getStudentGender().hashCode());
        result = prime * result + ((getStudentAge() == null) ? 0 : getStudentAge().hashCode());
        result = prime * result + ((getRegionId() == null) ? 0 : getRegionId().hashCode());
        result = prime * result + ((getStudentTotalCredits() == null) ? 0 : getStudentTotalCredits().hashCode());
        result = prime * result + ((getClassId() == null) ? 0 : getClassId().hashCode());
        result = prime * result + ((getEnrollmentDate() == null) ? 0 : getEnrollmentDate().hashCode());
        result = prime * result + ((getStudentStatus() == null) ? 0 : getStudentStatus().hashCode());
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
        sb.append(", regionId=").append(regionId);
        sb.append(", studentTotalCredits=").append(studentTotalCredits);
        sb.append(", classId=").append(classId);
        sb.append(", enrollmentDate=").append(enrollmentDate);
        sb.append(", studentStatus=").append(studentStatus);
        sb.append("]");
        return sb.toString();
    }
}