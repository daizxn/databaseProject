package org.example.database.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.database.entity.StudentFullInfo;
import org.example.database.service.StudentFullInfoService;
import org.example.database.mapper.StudentFullInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author daizxn
* @description 针对表【view_student_full_info】的数据库操作Service实现
* @createDate 2025-06-28 22:39:36
*/
@Service
public class StudentFullInfoServiceImpl extends ServiceImpl<StudentFullInfoMapper, StudentFullInfo>
    implements StudentFullInfoService{

}




