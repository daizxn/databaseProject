package org.example.database.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.database.entity.Students;
import org.example.database.entity.StudentFullInfo;
import org.example.database.service.StudentsService;
import org.example.database.mapper.StudentsMapper;
import org.springframework.stereotype.Service;

/**
* @author daizxn
* @description 针对表【daizx_students】的数据库操���Service实现
* @createDate 2025-06-28 22:16:10
*/
@Service
public class StudentsServiceImpl extends ServiceImpl<StudentsMapper, Students>
    implements StudentsService{

    @Override
    public IPage<StudentFullInfo> selectStudentFullInfoByPage(IPage<StudentFullInfo> page, StudentFullInfo studentFullInfo) {
        return baseMapper.selectStudentFullInfoByPage(page, studentFullInfo);
    }


}
