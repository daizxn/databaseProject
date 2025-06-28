package org.example.database.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.database.entity.Students;
import org.example.database.service.StudentsService;
import org.example.database.mapper.StudentsMapper;
import org.springframework.stereotype.Service;

/**
* @author daizxn
* @description 针对表【daizx_students】的数据库操作Service实现
* @createDate 2025-06-28 22:16:10
*/
@Service
public class StudentsServiceImpl extends ServiceImpl<StudentsMapper, Students>
    implements StudentsService{

}




