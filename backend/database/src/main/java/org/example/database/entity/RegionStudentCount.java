package org.example.database.entity;

import lombok.Data;

/**
 * 地区学生统计聚合类
 */
@Data
public class RegionStudentCount {
    /**
     * 地区ID
     */
    private Integer regionId;

    /**
     * 地区名称
     */
    private String regionName;

    /**
     * 学生总数
     */
    private Integer studentCount;

    public RegionStudentCount() {}

    public RegionStudentCount(Integer regionId, String regionName, Integer studentCount) {
        this.regionId = regionId;
        this.regionName = regionName;
        this.studentCount = studentCount;
    }
}
