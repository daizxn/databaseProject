package org.example.database.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.database.entity.Departments;
import org.example.database.service.DepartmentsService;
import org.example.database.mapper.DepartmentsMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author daizxn
* @description 针对表【daizx_departments】的数据库操作Service实现
* @createDate 2025-06-24 14:24:22
*/
@Service
public class DepartmentsServiceImpl extends ServiceImpl<DepartmentsMapper, Departments>
    implements DepartmentsService{

}




