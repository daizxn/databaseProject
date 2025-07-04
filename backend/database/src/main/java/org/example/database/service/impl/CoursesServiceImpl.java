package org.example.database.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.database.entity.Courses;
import org.example.database.entity.TeacherCourses;
import org.example.database.service.CoursesService;
import org.example.database.mapper.CoursesMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author daizxn
* @description 针对表【daizx_courses】的数据库操作Service实现
* @createDate 2025-07-03 20:52:09
*/
@Service
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses>
    implements CoursesService{

    @Override
    public IPage<TeacherCourses> selectTeacherCoursesByPage(IPage<TeacherCourses> page, TeacherCourses teacherCourses) {
        return baseMapper.selectTeacherCoursesByPage(page, teacherCourses);
    }

    // ========== 基本CRUD操作实现 ==========

    @Override
    public boolean addCourses(Courses courses) {
        return baseMapper.insertCourses(courses) > 0;
    }

    @Override
    public boolean deleteCoursesByNumber(String courseNumber) {
        return baseMapper.deleteCoursesByNumber(courseNumber) > 0;
    }

    @Override
    public boolean deleteCoursesById(Integer courseId) {
        return baseMapper.deleteCoursesById(courseId) > 0;
    }

    @Override
    public boolean deleteCoursesBatch(List<Integer> courseIds) {
        return baseMapper.deleteCoursesBatch(courseIds) > 0;
    }

    @Override
    public boolean updateCourses(Courses courses) {
        return baseMapper.updateCourses(courses) > 0;
    }

    @Override
    public boolean updateCoursesByNumber(Courses courses) {
        return baseMapper.updateCoursesByNumber(courses) > 0;
    }

    @Override
    public boolean updateCoursesBatch(List<Courses> coursesList) {
        int count = 0;
        for (Courses courses : coursesList) {
            count += baseMapper.updateCourses(courses);
        }
        return count == coursesList.size();
    }

    @Override
    public Courses selectCoursesById(Integer courseId) {
        return baseMapper.selectCoursesById(courseId);
    }

    @Override
    public Courses selectCoursesByNumber(String courseNumber) {
        return baseMapper.selectCoursesByNumber(courseNumber);
    }

    @Override
    public List<Courses> selectAllCourses() {
        return baseMapper.selectAllCourses();
    }

    @Override
    public List<Courses> selectCoursesByName(String courseName) {
        return baseMapper.selectCoursesByName(courseName);
    }

    @Override
    public List<Courses> selectCoursesByTeacher(String teacherNumber) {
        return baseMapper.selectCoursesByTeacher(teacherNumber);
    }

    @Override
    public List<Courses> selectCoursesByClassId(Integer classId) {
        return baseMapper.selectCoursesByClassId(classId);
    }

    @Override
    public IPage<Courses> selectCoursesByPage(IPage<Courses> page, Courses courses) {
        return baseMapper.selectCoursesByPage(page, courses);
    }

}
