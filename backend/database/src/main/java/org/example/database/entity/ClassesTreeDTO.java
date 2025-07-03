package org.example.database.entity;

import lombok.Data;
import java.util.List;

/**
 * 专业-班级二级分类DTO
 */
@Data
public class ClassesTreeDTO {
    /**
     * 值（专业ID或班级ID）
     */
    private String value;

    /**
     * 标签（专业名称或班级名称）
     */
    private String label;

    /**
     * 子级（班级列表）
     */
    private List<ClassesTreeDTO> children;

    public ClassesTreeDTO() {}

    public ClassesTreeDTO(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public ClassesTreeDTO(String value, String label, List<ClassesTreeDTO> children) {
        this.value = value;
        this.label = label;
        this.children = children;
    }
}
