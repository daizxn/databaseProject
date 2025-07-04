package org.example.database.service;

import org.example.database.entity.Courses;
import org.example.database.entity.TeacherCourses;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
* @author daizxn
* @description 针对表【daizx_courses】的数据库操作Service
* @createDate 2025-07-03 20:52:09
*/
public interface CoursesService extends IService<Courses> {

    /**
     * 分页查询课程和教师的关联信息
     * @param page 分页对象
     * @param teacherCourses 查询条件
     * @return 课程教师关联信息的分页结果
     */
    IPage<TeacherCourses> selectTeacherCoursesByPage(IPage<TeacherCourses> page, TeacherCourses teacherCourses);

    // ========== 基本CRUD操作 ==========

    /**
     * 新增课程
     * @param courses 课程信息
     * @return 是否成功
     */
    boolean addCourses(Courses courses);

    /**
     * 根据课程编号删除课程
     * @param courseNumber 课程编号
     * @return 是否成功
     */
    boolean deleteCoursesByNumber(String courseNumber);

    /**
     * 根据ID删除课程
     * @param courseId 课程ID
     * @return 是否成功
     */
    boolean deleteCoursesById(Integer courseId);

    /**
     * 批量删除课程
     * @param courseIds 课程ID列表
     * @return 是否成功
     */
    boolean deleteCoursesBatch(List<Integer> courseIds);

    /**
     * 更新课程信息
     * @param courses 课程信息
     * @return 是否成功
     */
    boolean updateCourses(Courses courses);

    /**
     * 根据课程编号更新课程信息
     * @param courses 课程信息
     * @return 是否成功
     */
    boolean updateCoursesByNumber(Courses courses);

    /**
     * 批量更新课程信息
     * @param coursesList 课程信息列表
     * @return 是否成功
     */
    boolean updateCoursesBatch(List<Courses> coursesList);

    /**
     * 根据ID查询课程
     * @param courseId 课程ID
     * @return 课程信息
     */
    Courses selectCoursesById(Integer courseId);

    /**
     * 根据课程编号查询课程
     * @param courseNumber 课程编号
     * @return 课程信息
     */
    Courses selectCoursesByNumber(String courseNumber);

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
    List<Courses> selectCoursesByName(String courseName);

    /**
     * 根据教师编号查询课程
     * @param teacherNumber 教师编号
     * @return 课程列表
     */
    List<Courses> selectCoursesByTeacher(String teacherNumber);

    /**
     * 根据班级ID查询课程
     * @param classId 班级ID
     * @return 课程列表
     */
    List<Courses> selectCoursesByClassId(Integer classId);

    /**
     * 分页查询课程信息
     * @param page 分页对象
     * @param courses 查询条件
     * @return 课程分页结果
     */
    IPage<Courses> selectCoursesByPage(IPage<Courses> page, Courses courses);

}
