package org.example.database.service;

import org.example.database.entity.Students;
import org.example.database.entity.StudentFullInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
* @author daizxn
* @description 针对表【daizx_students】的数据库操作Service
* @createDate 2025-06-28 22:16:10
*/
public interface StudentsService extends IService<Students> {

    /**
     * 分页查询学生完整信息��包含地区、班级、专业等关联信息）
     * @param page 分页对象
     * @param studentFullInfo 查询条件
     * @return 学生完整信息的分页结果
     */
    IPage<StudentFullInfo> selectStudentFullInfoByPage(IPage<StudentFullInfo> page, StudentFullInfo studentFullInfo);


}
