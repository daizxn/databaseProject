package org.example.database.service;

import org.example.database.entity.Classes;
import org.example.database.entity.ClassesTreeDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
* @author daizxn
* @description 针对表【daizx_classes】的数据库操作Service
* @createDate 2025-06-28 22:15:46
*/
public interface ClassesService extends IService<Classes> {

    /**
     * 查询按专业分组的班级列表，构建二级分类结构
     * @return 专业-班级二级分类树
     */
    List<ClassesTreeDTO> selectClassesListGroupByDepartment();

    // ========== 基本CRUD操作 ==========

    /**
     * 新增班级
     * @param classes 班级信息
     * @return 是否成功
     */
    boolean addClasses(Classes classes);

    /**
     * 根据ID删除班级
     * @param classId 班级ID
     * @return 是否成功
     */
    boolean deleteClassesById(Integer classId);

    /**
     * 批量删除班级
     * @param classIds 班级ID列表
     * @return 是否成功
     */
    boolean deleteClassesBatch(List<Integer> classIds);

    /**
     * 更新班级信息
     * @param classes 班级信息
     * @return 是否成功
     */
    boolean updateClasses(Classes classes);

    /**
     * 批量更新班级信息
     * @param classesList 班级信息列表
     * @return 是否成功
     */
    boolean updateClassesBatch(List<Classes> classesList);

    /**
     * 根据ID查询班级
     * @param classId 班级ID
     * @return 班级信息
     */
    Classes selectClassesById(Integer classId);

    /**
     * 查询所有班级
     * @return 班级列表
     */
    List<Classes> selectAllClasses();

    /**
     * 根据专业ID查询班级列表
     * @param departmentId 专业ID
     * @return 班级列表
     */
    List<Classes> selectClassesByDepartmentId(Integer departmentId);

    /**
     * 根据班级名称模糊查询
     * @param className 班级名称
     * @return 班级列表
     */
    List<Classes> selectClassesByName(String className);

    /**
     * 分页查询班级信息
     * @param page 分页对象
     * @param classes 查询条件
     * @return 班级分页结果
     */
    IPage<Classes> selectClassesByPage(IPage<Classes> page, Classes classes);

}
