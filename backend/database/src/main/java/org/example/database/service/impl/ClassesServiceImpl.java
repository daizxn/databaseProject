package org.example.database.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.database.entity.Classes;
import org.example.database.entity.ClassesTreeDTO;
import org.example.database.service.ClassesService;
import org.example.database.mapper.ClassesMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
* @author daizxn
* @description 针对表【daizx_classes】的数据库操作Service实现
* @createDate 2025-06-28 22:15:46
*/
@Service
public class ClassesServiceImpl extends ServiceImpl<ClassesMapper, Classes>
    implements ClassesService{

    @Override
    public List<ClassesTreeDTO> selectClassesListGroupByDepartment() {
        // 从Mapper获取原始数据
        List<Map<String, Object>> departmentClassesInfo = baseMapper.selectDepartmentClassesInfo();

        // 按专业分组
        Map<Integer, List<Map<String, Object>>> groupedByDepartment = departmentClassesInfo.stream()
                .collect(Collectors.groupingBy(item -> (Integer) item.get("departmentId")));

        // 构建树形结构
        List<ClassesTreeDTO> result = new ArrayList<>();

        for (Map.Entry<Integer, List<Map<String, Object>>> entry : groupedByDepartment.entrySet()) {
            Integer departmentId = entry.getKey();
            List<Map<String, Object>> departmentData = entry.getValue();

            // 获取专业信息（取第一条记录的专业信息）
            Map<String, Object> firstRecord = departmentData.get(0);
            String departmentName = (String) firstRecord.get("departmentName");

            // 创建专业节点
            ClassesTreeDTO departmentNode = new ClassesTreeDTO(
                    departmentId.toString(),
                    departmentName
            );

            // 构建班级子节点列表
            List<ClassesTreeDTO> children = departmentData.stream()
                    .filter(item -> item.get("classId") != null) // 过滤掉没有班级的专业
                    .map(item -> new ClassesTreeDTO(
                            item.get("classId").toString(),
                            (String) item.get("className")
                    ))
                    .collect(Collectors.toList());

            departmentNode.setChildren(children);
            result.add(departmentNode);
        }

        return result;
    }

    // ========== 基本CRUD操作实现 ==========

    @Override
    public boolean addClasses(Classes classes) {
        return baseMapper.insertClasses(classes) > 0;
    }

    @Override
    public boolean deleteClassesById(Integer classId) {
        return baseMapper.deleteClassesById(classId) > 0;
    }

    @Override
    public boolean deleteClassesBatch(List<Integer> classIds) {
        return baseMapper.deleteClassesBatch(classIds) > 0;
    }

    @Override
    public boolean updateClasses(Classes classes) {
        return baseMapper.updateClasses(classes) > 0;
    }

    @Override
    public boolean updateClassesBatch(List<Classes> classesList) {
        int count = 0;
        for (Classes classes : classesList) {
            count += baseMapper.updateClasses(classes);
        }
        return count == classesList.size();
    }

    @Override
    public Classes selectClassesById(Integer classId) {
        return baseMapper.selectClassesById(classId);
    }

    @Override
    public List<Classes> selectAllClasses() {
        return baseMapper.selectAllClasses();
    }

    @Override
    public List<Classes> selectClassesByDepartmentId(Integer departmentId) {
        return baseMapper.selectClassesByDepartmentId(departmentId);
    }

    @Override
    public List<Classes> selectClassesByName(String className) {
        return baseMapper.selectClassesByName(className);
    }

    @Override
    public IPage<Classes> selectClassesByPage(IPage<Classes> page, Classes classes) {
        return baseMapper.selectClassesByPage(page, classes);
    }

}
