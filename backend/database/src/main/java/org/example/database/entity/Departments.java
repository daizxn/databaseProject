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
    private Integer dzxDid;

    /**
     * 
     */
    private String dzxDname;

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
        return (this.getDzxDid() == null ? other.getDzxDid() == null : this.getDzxDid().equals(other.getDzxDid()))
            && (this.getDzxDname() == null ? other.getDzxDname() == null : this.getDzxDname().equals(other.getDzxDname()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDzxDid() == null) ? 0 : getDzxDid().hashCode());
        result = prime * result + ((getDzxDname() == null) ? 0 : getDzxDname().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dzxDid=").append(dzxDid);
        sb.append(", dzxDname=").append(dzxDname);
        sb.append("]");
        return sb.toString();
    }
}