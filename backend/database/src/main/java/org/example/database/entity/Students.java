package org.example.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
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
    @TableId
    private String dzxStudentNumber;

    /**
     * 
     */
    private String dzxStudentName;

    /**
     * 
     */
    private String dzxStudentGender;

    /**
     * 
     */
    private Integer dzxStudentAge;

    /**
     * 
     */
    private Integer dzxStudentRegionId;

    /**
     * 
     */
    private Integer dzxStudentTotalCredits;

    /**
     * 
     */
    private Integer dzxClassId;

    /**
     * 
     */
    private LocalDateTime dzxEnrollmentDate;

    /**
     * 
     */
    private String dzxStudentStatus;

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
        return (this.getDzxStudentNumber() == null ? other.getDzxStudentNumber() == null : this.getDzxStudentNumber().equals(other.getDzxStudentNumber()))
            && (this.getDzxStudentName() == null ? other.getDzxStudentName() == null : this.getDzxStudentName().equals(other.getDzxStudentName()))
            && (this.getDzxStudentGender() == null ? other.getDzxStudentGender() == null : this.getDzxStudentGender().equals(other.getDzxStudentGender()))
            && (this.getDzxStudentAge() == null ? other.getDzxStudentAge() == null : this.getDzxStudentAge().equals(other.getDzxStudentAge()))
            && (this.getDzxStudentRegionId() == null ? other.getDzxStudentRegionId() == null : this.getDzxStudentRegionId().equals(other.getDzxStudentRegionId()))
            && (this.getDzxStudentTotalCredits() == null ? other.getDzxStudentTotalCredits() == null : this.getDzxStudentTotalCredits().equals(other.getDzxStudentTotalCredits()))
            && (this.getDzxClassId() == null ? other.getDzxClassId() == null : this.getDzxClassId().equals(other.getDzxClassId()))
            && (this.getDzxEnrollmentDate() == null ? other.getDzxEnrollmentDate() == null : this.getDzxEnrollmentDate().equals(other.getDzxEnrollmentDate()))
            && (this.getDzxStudentStatus() == null ? other.getDzxStudentStatus() == null : this.getDzxStudentStatus().equals(other.getDzxStudentStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDzxStudentNumber() == null) ? 0 : getDzxStudentNumber().hashCode());
        result = prime * result + ((getDzxStudentName() == null) ? 0 : getDzxStudentName().hashCode());
        result = prime * result + ((getDzxStudentGender() == null) ? 0 : getDzxStudentGender().hashCode());
        result = prime * result + ((getDzxStudentAge() == null) ? 0 : getDzxStudentAge().hashCode());
        result = prime * result + ((getDzxStudentRegionId() == null) ? 0 : getDzxStudentRegionId().hashCode());
        result = prime * result + ((getDzxStudentTotalCredits() == null) ? 0 : getDzxStudentTotalCredits().hashCode());
        result = prime * result + ((getDzxClassId() == null) ? 0 : getDzxClassId().hashCode());
        result = prime * result + ((getDzxEnrollmentDate() == null) ? 0 : getDzxEnrollmentDate().hashCode());
        result = prime * result + ((getDzxStudentStatus() == null) ? 0 : getDzxStudentStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dzxStudentNumber=").append(dzxStudentNumber);
        sb.append(", dzxStudentName=").append(dzxStudentName);
        sb.append(", dzxStudentGender=").append(dzxStudentGender);
        sb.append(", dzxStudentAge=").append(dzxStudentAge);
        sb.append(", dzxStudentRegionId=").append(dzxStudentRegionId);
        sb.append(", dzxStudentTotalCredits=").append(dzxStudentTotalCredits);
        sb.append(", dzxClassId=").append(dzxClassId);
        sb.append(", dzxEnrollmentDate=").append(dzxEnrollmentDate);
        sb.append(", dzxStudentStatus=").append(dzxStudentStatus);
        sb.append("]");
        return sb.toString();
    }
}