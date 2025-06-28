package org.example.database.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.example.database.entity.Scores;
import org.example.database.service.ScoresService;
import org.example.database.mapper.ScoresMapper;
import org.springframework.stereotype.Service;

/**
* @author daizxn
* @description 针对表【daizx_scores】的数据库操作Service实现
* @createDate 2025-06-28 22:16:10
*/
@Service
public class ScoresServiceImpl extends MppServiceImpl<ScoresMapper, Scores>
    implements ScoresService{

}




