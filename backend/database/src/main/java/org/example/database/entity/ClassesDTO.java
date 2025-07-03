package org.example.database.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassesDTO {
    private Integer classId; // 班级ID
    private String className; // 班级名称
}
