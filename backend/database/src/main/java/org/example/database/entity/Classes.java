package org.example.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName daizx_classes
 */
@TableName(value ="daizx_classes")
@Data
public class Classes {
    /**
     * 
     */
    @TableId
    private Integer dzxClassId;

    /**
     * 
     */
    private String dzxClassName;

    /**
     * 
     */
    private Integer dzxDepartmentId;

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
        Classes other = (Classes) that;
        return (this.getDzxClassId() == null ? other.getDzxClassId() == null : this.getDzxClassId().equals(other.getDzxClassId()))
            && (this.getDzxClassName() == null ? other.getDzxClassName() == null : this.getDzxClassName().equals(other.getDzxClassName()))
            && (this.getDzxDepartmentId() == null ? other.getDzxDepartmentId() == null : this.getDzxDepartmentId().equals(other.getDzxDepartmentId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDzxClassId() == null) ? 0 : getDzxClassId().hashCode());
        result = prime * result + ((getDzxClassName() == null) ? 0 : getDzxClassName().hashCode());
        result = prime * result + ((getDzxDepartmentId() == null) ? 0 : getDzxDepartmentId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dzxClassId=").append(dzxClassId);
        sb.append(", dzxClassName=").append(dzxClassName);
        sb.append(", dzxDepartmentId=").append(dzxDepartmentId);
        sb.append("]");
        return sb.toString();
    }
}