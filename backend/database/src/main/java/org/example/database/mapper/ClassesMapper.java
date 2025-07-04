package org.example.database.mapper;

import org.example.database.entity.Classes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author daizxn
* @description 针对表【daizx_classes】的数据库操作Mapper
* @createDate 2025-06-28 22:15:46
* @Entity org.example.database.entity.Classes
*/
public interface ClassesMapper extends BaseMapper<Classes> {

    /**
     * 查询专业和班级的关联信息
     * @return 包含专业和班级信息的Map列表
     */
    List<Map<String, Object>> selectDepartmentClassesInfo();

    // ========== 基本CRUD操作 ==========

    /**
     * 新增班级
     * @param classes 班级信息
     * @return 影响行数
     */
    int insertClasses(Classes classes);

    /**
     * 根据ID删除班级
     * @param classId 班级ID
     * @return 影响行数
     */
    int deleteClassesById(@Param("classId") Integer classId);

    /**
     * 批量删除班级
     * @param classIds 班级ID列表
     * @return 影响行数
     */
    int deleteClassesBatch(@Param("classIds") List<Integer> classIds);

    /**
     * 更新班级信息
     * @param classes 班级信息
     * @return 影响行数
     */
    int updateClasses(Classes classes);

    /**
     * 根据ID查询班级
     * @param classId 班级ID
     * @return 班级信息
     */
    Classes selectClassesById(@Param("classId") Integer classId);

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
    List<Classes> selectClassesByDepartmentId(@Param("departmentId") Integer departmentId);

    /**
     * 根据班级名称模糊查询
     * @param className 班级名称
     * @return 班级列表
     */
    List<Classes> selectClassesByName(@Param("className") String className);

    /**
     * 分页查询班级信息
     * @param page 分页对象
     * @param classes 查询条件
     * @return 班级分页结果
     */
    IPage<Classes> selectClassesByPage(IPage<Classes> page, @Param("classes") Classes classes);

}
