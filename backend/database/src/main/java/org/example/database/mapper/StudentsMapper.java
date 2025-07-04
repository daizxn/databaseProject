package org.example.database.mapper;

import org.example.database.entity.Students;
import org.example.database.entity.StudentFullInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
* @author daizxn
* @description 针对表【daizx_students】的数据库操作Mapper
* @createDate 2025-06-28 22:16:10
* @Entity org.example.database.entity.Students
*/
public interface StudentsMapper extends BaseMapper<Students> {

    /**
     * 分页查询学生完整信息（包含地区、班级、专业等关联信息）
     * @param page 分页对象
     * @param studentFullInfo 查询条件
     * @return 学生完整信息的分页结果
     */
    IPage<StudentFullInfo> selectStudentFullInfoByPage(IPage<StudentFullInfo> page, @Param("student") StudentFullInfo studentFullInfo);


}
