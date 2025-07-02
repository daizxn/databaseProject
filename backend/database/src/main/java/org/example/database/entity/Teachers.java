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
    @TableId(value = "dzx_teacher_number")
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
    @TableField(value = "dzx_teacher_age")
    private Integer teacherAge;

    /**
     * 
     */
    @TableField(value = "dzx_teacher_title")
    private String teacherTitle;

    /**
     * 
     */
    @TableField(value = "dzx_teacher_phone")
    private String teacherPhone;

    /**
     * 
     */
    @TableField(value = "dzx_hire_date")
    private LocalDate hireDate;

    /**
     * 
     */
    @TableField(value = "dzx_teacher_status")
    private String teacherStatus;

    /**
     * 
     */
    @TableField(value = "dzx_teacher_email")
    private Integer teacherEmail;

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
        return (this.getTeacherNumber() == null ? other.getTeacherNumber() == null : this.getTeacherNumber().equals(other.getTeacherNumber()))
            && (this.getTeacherName() == null ? other.getTeacherName() == null : this.getTeacherName().equals(other.getTeacherName()))
            && (this.getTeacherGender() == null ? other.getTeacherGender() == null : this.getTeacherGender().equals(other.getTeacherGender()))
            && (this.getTeacherAge() == null ? other.getTeacherAge() == null : this.getTeacherAge().equals(other.getTeacherAge()))
            && (this.getTeacherTitle() == null ? other.getTeacherTitle() == null : this.getTeacherTitle().equals(other.getTeacherTitle()))
            && (this.getTeacherPhone() == null ? other.getTeacherPhone() == null : this.getTeacherPhone().equals(other.getTeacherPhone()))
            && (this.getHireDate() == null ? other.getHireDate() == null : this.getHireDate().equals(other.getHireDate()))
            && (this.getTeacherStatus() == null ? other.getTeacherStatus() == null : this.getTeacherStatus().equals(other.getTeacherStatus()))
            && (this.getTeacherEmail() == null ? other.getTeacherEmail() == null : this.getTeacherEmail().equals(other.getTeacherEmail()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTeacherNumber() == null) ? 0 : getTeacherNumber().hashCode());
        result = prime * result + ((getTeacherName() == null) ? 0 : getTeacherName().hashCode());
        result = prime * result + ((getTeacherGender() == null) ? 0 : getTeacherGender().hashCode());
        result = prime * result + ((getTeacherAge() == null) ? 0 : getTeacherAge().hashCode());
        result = prime * result + ((getTeacherTitle() == null) ? 0 : getTeacherTitle().hashCode());
        result = prime * result + ((getTeacherPhone() == null) ? 0 : getTeacherPhone().hashCode());
        result = prime * result + ((getHireDate() == null) ? 0 : getHireDate().hashCode());
        result = prime * result + ((getTeacherStatus() == null) ? 0 : getTeacherStatus().hashCode());
        result = prime * result + ((getTeacherEmail() == null) ? 0 : getTeacherEmail().hashCode());
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
        sb.append(", teacherAge=").append(teacherAge);
        sb.append(", teacherTitle=").append(teacherTitle);
        sb.append(", teacherPhone=").append(teacherPhone);
        sb.append(", hireDate=").append(hireDate);
        sb.append(", teacherStatus=").append(teacherStatus);
        sb.append(", teacherEmail=").append(teacherEmail);
        sb.append("]");
        return sb.toString();
    }
}