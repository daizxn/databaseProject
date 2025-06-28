package org.example.database.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.database.entity.Classes;
import org.example.database.service.ClassesService;
import org.example.database.mapper.ClassesMapper;
import org.springframework.stereotype.Service;

/**
* @author daizxn
* @description 针对表【daizx_classes】的数据库操作Service实现
* @createDate 2025-06-28 21:42:09
*/
@Service
public class ClassesServiceImpl extends ServiceImpl<ClassesMapper, Classes>
    implements ClassesService{

}




