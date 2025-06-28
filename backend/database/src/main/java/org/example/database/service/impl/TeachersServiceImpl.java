package org.example.database.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.database.entity.Teachers;
import org.example.database.service.TeachersService;
import org.example.database.mapper.TeachersMapper;
import org.springframework.stereotype.Service;

/**
* @author daizxn
* @description 针对表【daizx_teachers】的数据库操作Service实现
* @createDate 2025-06-28 21:43:26
*/
@Service
public class TeachersServiceImpl extends ServiceImpl<TeachersMapper, Teachers>
    implements TeachersService{

}




