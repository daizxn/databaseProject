package org.example.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName daizx_departments
 */
@TableName(value ="daizx_departments")
@Data
public class Departments {
    /**
     * 
     */
    @TableId
    private Integer dzxDepartmentId;

    /**
     * 
     */
    private String dzxDepartmentName;

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
        Departments other = (Departments) that;
        return (this.getDzxDepartmentId() == null ? other.getDzxDepartmentId() == null : this.getDzxDepartmentId().equals(other.getDzxDepartmentId()))
            && (this.getDzxDepartmentName() == null ? other.getDzxDepartmentName() == null : this.getDzxDepartmentName().equals(other.getDzxDepartmentName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDzxDepartmentId() == null) ? 0 : getDzxDepartmentId().hashCode());
        result = prime * result + ((getDzxDepartmentName() == null) ? 0 : getDzxDepartmentName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dzxDepartmentId=").append(dzxDepartmentId);
        sb.append(", dzxDepartmentName=").append(dzxDepartmentName);
        sb.append("]");
        return sb.toString();
    }
}