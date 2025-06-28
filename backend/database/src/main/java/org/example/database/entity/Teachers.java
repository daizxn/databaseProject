package org.example.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import lombok.Data;

/**
 * 
 * @TableName daizx_teachers
 */
@TableName(value ="daizx_teachers")
@Data
public class Teachers {
    /**
     * 
     */
    @TableId
    private String dzxTeacherNumber;

    /**
     * 
     */
    private String dzxTeacherName;

    /**
     * 
     */
    private String dzxTeacherGender;

    /**
     * 
     */
    private Integer dzxTeacherAge;

    /**
     * 
     */
    private String dzxTeacherTitle;

    /**
     * 
     */
    private String dzxTeacherPhone;

    /**
     * 
     */
    private LocalDate dzxHireDate;

    /**
     * 
     */
    private String dzxTeacherStatus;

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
        Teachers other = (Teachers) that;
        return (this.getDzxTeacherNumber() == null ? other.getDzxTeacherNumber() == null : this.getDzxTeacherNumber().equals(other.getDzxTeacherNumber()))
            && (this.getDzxTeacherName() == null ? other.getDzxTeacherName() == null : this.getDzxTeacherName().equals(other.getDzxTeacherName()))
            && (this.getDzxTeacherGender() == null ? other.getDzxTeacherGender() == null : this.getDzxTeacherGender().equals(other.getDzxTeacherGender()))
            && (this.getDzxTeacherAge() == null ? other.getDzxTeacherAge() == null : this.getDzxTeacherAge().equals(other.getDzxTeacherAge()))
            && (this.getDzxTeacherTitle() == null ? other.getDzxTeacherTitle() == null : this.getDzxTeacherTitle().equals(other.getDzxTeacherTitle()))
            && (this.getDzxTeacherPhone() == null ? other.getDzxTeacherPhone() == null : this.getDzxTeacherPhone().equals(other.getDzxTeacherPhone()))
            && (this.getDzxHireDate() == null ? other.getDzxHireDate() == null : this.getDzxHireDate().equals(other.getDzxHireDate()))
            && (this.getDzxTeacherStatus() == null ? other.getDzxTeacherStatus() == null : this.getDzxTeacherStatus().equals(other.getDzxTeacherStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDzxTeacherNumber() == null) ? 0 : getDzxTeacherNumber().hashCode());
        result = prime * result + ((getDzxTeacherName() == null) ? 0 : getDzxTeacherName().hashCode());
        result = prime * result + ((getDzxTeacherGender() == null) ? 0 : getDzxTeacherGender().hashCode());
        result = prime * result + ((getDzxTeacherAge() == null) ? 0 : getDzxTeacherAge().hashCode());
        result = prime * result + ((getDzxTeacherTitle() == null) ? 0 : getDzxTeacherTitle().hashCode());
        result = prime * result + ((getDzxTeacherPhone() == null) ? 0 : getDzxTeacherPhone().hashCode());
        result = prime * result + ((getDzxHireDate() == null) ? 0 : getDzxHireDate().hashCode());
        result = prime * result + ((getDzxTeacherStatus() == null) ? 0 : getDzxTeacherStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dzxTeacherNumber=").append(dzxTeacherNumber);
        sb.append(", dzxTeacherName=").append(dzxTeacherName);
        sb.append(", dzxTeacherGender=").append(dzxTeacherGender);
        sb.append(", dzxTeacherAge=").append(dzxTeacherAge);
        sb.append(", dzxTeacherTitle=").append(dzxTeacherTitle);
        sb.append(", dzxTeacherPhone=").append(dzxTeacherPhone);
        sb.append(", dzxHireDate=").append(dzxHireDate);
        sb.append(", dzxTeacherStatus=").append(dzxTeacherStatus);
        sb.append("]");
        return sb.toString();
    }
}