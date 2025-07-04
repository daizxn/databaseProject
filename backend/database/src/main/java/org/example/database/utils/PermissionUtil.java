package org.example.database.utils;

import org.example.database.common.enums.ResultCodeEnum;
import org.example.database.exception.PermissionDeniedException;

/**
 * 权限验证工具类
 * 基于JWT Token中的用户信息进行权限验证
 */
public class PermissionUtil {

    // 角色常量
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_TEACHER = "teacher";
    public static final String ROLE_STUDENT = "student";

    /**
     * 检查是否为管理员
     */
    public static boolean isAdmin() {
        return UserInfoUtil.hasRole(ROLE_ADMIN);
    }

    /**
     * 检查是否为教师
     */
    public static boolean isTeacher() {
        return UserInfoUtil.hasRole(ROLE_TEACHER);
    }

    /**
     * 检查是否为学生
     */
    public static boolean isStudent() {
        return UserInfoUtil.hasRole(ROLE_STUDENT);
    }

    /**
     * 要求管理员权限
     */
    public static void requireAdmin() {
        if (!isAdmin()) {
            throw new PermissionDeniedException("需要管理员权限");
        }
    }

    /**
     * 要求教师或管理员权限
     */
    public static void requireTeacherOrAdmin() {
        if (!isTeacher() && !isAdmin()) {
            throw new PermissionDeniedException("需要教师或管理员权限");
        }
    }

    /**
     * 要求学生或管理员权限
     */
    public static void requireStudentOrAdmin() {
        if (!isStudent() && !isAdmin()) {
            throw new PermissionDeniedException("需要学生或管理员权限");
        }
    }

    /**
     * 检查学生是否可以访问指定学生的信息
     * 学生只能访问自己的信息，管理员可以访问所有
     */
    public static void checkStudentAccess(String targetStudentNumber) {
        if (isAdmin()) {
            return; // 管理员可以访问所有信息
        }

        if (isStudent()) {
            String currentRefId = UserInfoUtil.getCurrentRefId();
            if (currentRefId == null || !currentRefId.equals(targetStudentNumber)) {
                throw new PermissionDeniedException("学生只能查询自己的信息");
            }
        } else {
            throw new PermissionDeniedException("权限不足");
        }
    }

    /**
     * 检查教师是否可以访问指定教师的信息
     * 教师只能访问自己的信息，管理员可以访问所有
     */
    public static void checkTeacherAccess(String targetTeacherNumber) {
        if (isAdmin()) {
            return; // 管理员可以访问所有信息
        }

        if (isTeacher()) {
            String currentRefId = UserInfoUtil.getCurrentRefId();
            if (currentRefId == null || !currentRefId.equals(targetTeacherNumber)) {
                throw new PermissionDeniedException("教师只能查询自己的信息");
            }
        } else {
            throw new PermissionDeniedException("权限不足");
        }
    }

    /**
     * 检查是否可以修改成绩
     * 教师只能修改自己课程的学生成绩，管理员可以修改所有
     */
    public static void checkScoreModifyPermission(String courseTeacherNumber) {
        if (isAdmin()) {
            return; // 管理员可以修改所有成绩
        }

        if (isTeacher()) {
            String currentRefId = UserInfoUtil.getCurrentRefId();
            if (currentRefId == null || !currentRefId.equals(courseTeacherNumber)) {
                throw new PermissionDeniedException("教师只能修改自己课程的学生成绩");
            }
        } else {
            throw new PermissionDeniedException("只有教师和管理员可以修改成绩");
        }
    }

    /**
     * 获取当前用户的学生编号（如果是学生）
     */
    public static String getCurrentStudentNumber() {
        if (isStudent()) {
            return UserInfoUtil.getCurrentRefId();
        }
        return null;
    }

    /**
     * 获取当前用户的教师编号（如果是教师）
     */
    public static String getCurrentTeacherNumber() {
        if (isTeacher()) {
            return UserInfoUtil.getCurrentRefId();
        }
        return null;
    }

    /**
     * 检查教师是否可以访问指定课程
     * 教师只能访问自己开设的课程，管理员可以访问所有
     */
    public static void checkTeacherCourseAccess(String courseTeacherNumber) {
        if (isAdmin()) {
            return; // 管理员可以访问所有课程
        }

        if (isTeacher()) {
            String currentRefId = UserInfoUtil.getCurrentRefId();
            if (currentRefId == null || !currentRefId.equals(courseTeacherNumber)) {
                throw new PermissionDeniedException("教师只能查询和修改自己开设的课程");
            }
        } else {
            throw new PermissionDeniedException("权限不足");
        }
    }

    /**
     * 检查是否可以修改课程信息
     * 只有管理员可以修改课程基本信息
     */
    public static void checkCourseModifyPermission() {
        if (!isAdmin()) {
            throw new PermissionDeniedException("只有管理员可以修改课程信息");
        }
    }

    /**
     * 检查是否可以访问所有学生成绩
     * 学生只能查看自己的成绩，教师只能查看自己课程的学生成绩，管理员可以查看所有
     */
    public static void checkAllScoresAccess() {
        if (isStudent()) {
            throw new PermissionDeniedException("学生只能查询自己的成绩");
        }
        // 教师和管理员可以查看（但教师需要在业务逻辑中过滤自己的课程）
    }

    /**
     * 检查学生成绩访问权限
     * 学生只能访问自己的成绩
     */
    public static void checkStudentScoreAccess(String targetStudentNumber) {
        if (isAdmin()) {
            return; // 管理员可以访问所有成绩
        }

        if (isStudent()) {
            String currentRefId = UserInfoUtil.getCurrentRefId();
            if (currentRefId == null || !currentRefId.equals(targetStudentNumber)) {
                throw new PermissionDeniedException("学生只能查询自己的成绩");
            }
        }
        // 教师权限在业务逻辑中处理（需要验证是否为自己课程的学生）
    }

    /**
     * 检查是否可以管理教师信息
     * 只有管理员可以添加、删除、批量操作教师
     */
    public static void checkTeacherManagementPermission() {
        if (!isAdmin()) {
            throw new PermissionDeniedException("只有管理员可以进行教师管理操作");
        }
    }

    /**
     * 检查是否可以访问部门信息
     * 学生和教师可以查看，但只有管理员可以修改
     */
    public static void checkDepartmentModifyPermission() {
        if (!isAdmin()) {
            throw new PermissionDeniedException("只有管理员可以修改部门信息");
        }
    }

    /**
     * 检查是否可以访问班级信息
     * 学生只能查看自己班级信息，教师和管理员可以查看所有
     */
    public static void checkClassViewPermission() {
        // 所有角色都可以查看班级信息，但学生在业务逻辑中会被限制为自己的班级
    }

    /**
     * 检查是否可以修改班级信息
     * 只有管理员可以修改班级信息
     */
    public static void checkClassModifyPermission() {
        if (!isAdmin()) {
            throw new PermissionDeniedException("只有管理员可以修改班级信息");
        }
    }

    /**
     * 检查是否可以访问地区信息
     * 所有用户都可以查看地区信息，但只有管理员可以修改
     */
    public static void checkRegionModifyPermission() {
        if (!isAdmin()) {
            throw new PermissionDeniedException("只有管理员可以修改地区信息");
        }
    }

    /**
     * 获取当前学生的班级ID（需要从数据库查询）
     * 这个方法需要在service层实现具体逻辑
     */
    public static String getCurrentStudentClassId() {
        // 这个方法的具体实现需要在service层完成
        // 这里只是提供接口
        return null;
    }

    /**
     * 获取当前用户角色
     */
    public static String getCurrentRole() {
        String roles = UserInfoUtil.getCurrentRoles();
        if (roles != null) {
            if (roles.contains(ROLE_ADMIN)) return ROLE_ADMIN;
            if (roles.contains(ROLE_TEACHER)) return ROLE_TEACHER;
            if (roles.contains(ROLE_STUDENT)) return ROLE_STUDENT;
        }
        return null;
    }

    /**
     * 检查是否有权限访问用户管理功能
     */
    public static void checkUserManagementPermission() {
        if (!isAdmin()) {
            throw new PermissionDeniedException("只有管理员可以进行用户管理操作");
        }
    }
}
