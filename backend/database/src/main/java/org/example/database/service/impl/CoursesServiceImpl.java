package org.example.database.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.database.entity.Courses;
import org.example.database.service.CoursesService;
import org.example.database.mapper.CoursesMapper;
import org.springframework.stereotype.Service;

/**
* @author daizxn
* @description 针对表【daizx_courses】的数据库操作Service实现
* @createDate 2025-06-28 22:15:46
*/
@Service
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses>
    implements CoursesService{

}




