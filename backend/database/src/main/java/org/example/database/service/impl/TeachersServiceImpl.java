package org.example.database.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.database.entity.Teachers;
import org.example.database.service.TeachersService;
import org.example.database.mapper.TeachersMapper;
import org.springframework.stereotype.Service;

/**
* @author daizxn
* @description 针对表【daizx_teachers】的数据库操作Service实现
* @createDate 2025-07-02 10:36:04
*/
@Service
public class TeachersServiceImpl extends ServiceImpl<TeachersMapper, Teachers>
    implements TeachersService{

}




