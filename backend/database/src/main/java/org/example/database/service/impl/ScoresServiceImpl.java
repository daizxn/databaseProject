package org.example.database.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.database.entity.Scores;
import org.example.database.entity.StudentCourseTeacherScores;
import org.example.database.entity.StudentScoreSimpleDTO;
import org.example.database.entity.StudentScoresDTO;
import org.example.database.service.ScoresService;
import org.example.database.mapper.ScoresMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author daizxn
* @description 针对表【daizx_scores】的数据库操作Service实现
* @createDate 2025-06-28 22:16:10
*/
@Service
public class ScoresServiceImpl extends MppServiceImpl<ScoresMapper, Scores>
    implements ScoresService{

    @Override
    public IPage<StudentCourseTeacherScores> selectStudentScoresByPage(IPage<StudentCourseTeacherScores> page, StudentScoresDTO studentScoresDTO) {
        return baseMapper.selectStudentScoresByPage(page, studentScoresDTO);
    }

    @Override
    public List<StudentScoreSimpleDTO> selectStudentsByCourseNumber(String courseNumber) {
        return baseMapper.selectStudentsByCourseNumber(courseNumber);
    }

}
