package org.example.database.mapper;

import org.example.database.entity.Courses;
import org.example.database.entity.TeacherCourses;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author daizxn
* @description 针对表【daizx_courses】的数据库操作Mapper
* @createDate 2025-07-03 20:52:09
* @Entity org.example.database.entity.Courses
*/
public interface CoursesMapper extends BaseMapper<Courses> {

    /**
     * 分页查询课程和教师的关联信息
     * @param page 分页对象
     * @param teacherCourses 查询条件
     * @return 课程教师关联信息的分页结果
     */
    IPage<TeacherCourses> selectTeacherCoursesByPage(IPage<TeacherCourses> page, @Param("teacherCourses") TeacherCourses teacherCourses);

    // ========== 基本CRUD操作 ==========

    /**
     * 新增课程
     * @param courses 课程信息
     * @return 影响行数
     */
    int insertCourses(Courses courses);

    /**
     * 根据课程编号删除课程
     * @param courseNumber 课程编号
     * @return 影响行数
     */
    int deleteCoursesByNumber(@Param("courseNumber") String courseNumber);

    /**
     * 根据ID删除课程
     * @param courseId 课程ID
     * @return 影响行数
     */
    int deleteCoursesById(@Param("courseId") Integer courseId);

    /**
     * 批量删除课程
     * @param courseIds 课程ID列表
     * @return 影响行数
     */
    int deleteCoursesBatch(@Param("courseIds") List<Integer> courseIds);

    /**
     * 更新课程信息
     * @param courses 课程信息
     * @return 影响行数
     */
    int updateCourses(Courses courses);

    /**
     * 根据课程编号更新课程信息
     * @param courses 课程信息
     * @return 影响行数
     */
    int updateCoursesByNumber(Courses courses);

    /**
     * 根据ID查询课程
     * @param courseId 课程ID
     * @return 课程信息
     */
    Courses selectCoursesById(@Param("courseId") Integer courseId);

    /**
     * 根据课程编号查询课程
     * @param courseNumber 课程编号
     * @return 课程信息
     */
    Courses selectCoursesByNumber(@Param("courseNumber") String courseNumber);

    /**
     * 查询所有课程
     * @return 课程列表
     */
    List<Courses> selectAllCourses();

    /**
     * 根据课程名称模糊查询
     * @param courseName 课程名称
     * @return 课程列表
     */
    List<Courses> selectCoursesByName(@Param("courseName") String courseName);

    /**
     * 根据教师编号查询课程
     * @param teacherNumber 教师编号
     * @return 课程列表
     */
    List<Courses> selectCoursesByTeacher(@Param("teacherNumber") String teacherNumber);

    /**
     * 根据班级ID查询课程
     * @param classId 班级ID
     * @return 课程列表
     */
    List<Courses> selectCoursesByClassId(@Param("classId") Integer classId);

    /**
     * 分页查询课程信息
     * @param page 分页对象
     * @param courses 查询条件
     * @return 课程分页结果
     */
    IPage<Courses> selectCoursesByPage(IPage<Courses> page, @Param("courses") Courses courses);

}
