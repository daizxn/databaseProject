package org.example.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName daizx_regions
 */
@TableName(value ="daizx_regions")
@Data
public class Regions {
    /**
     * 
     */
    @TableId
    private Integer dzxRegionId;

    /**
     * 
     */
    private String dzxRegionName;

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
        Regions other = (Regions) that;
        return (this.getDzxRegionId() == null ? other.getDzxRegionId() == null : this.getDzxRegionId().equals(other.getDzxRegionId()))
            && (this.getDzxRegionName() == null ? other.getDzxRegionName() == null : this.getDzxRegionName().equals(other.getDzxRegionName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDzxRegionId() == null) ? 0 : getDzxRegionId().hashCode());
        result = prime * result + ((getDzxRegionName() == null) ? 0 : getDzxRegionName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dzxRegionId=").append(dzxRegionId);
        sb.append(", dzxRegionName=").append(dzxRegionName);
        sb.append("]");
        return sb.toString();
    }
}