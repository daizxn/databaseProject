package org.example.database.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.database.entity.StudentCourseTeacherScores;
import org.example.database.entity.ScoreStatistics;
import org.example.database.service.StudentCourseTeacherScoresService;
import org.example.database.mapper.StudentCourseTeacherScoresMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
* @author daizxn
* @description 针对表【view_student_course_teacher_scores】的数据库操作Service实现
* @createDate 2025-07-05 01:29:26
*/
@Service
public class StudentCourseTeacherScoresServiceImpl extends ServiceImpl<StudentCourseTeacherScoresMapper, StudentCourseTeacherScores>
    implements StudentCourseTeacherScoresService{

    @Override
    public ScoreStatistics getScoreStatistics(String studentNumber, String academicYear) {
        // 1. 获取该学生在指定学年的所有成绩记录
        QueryWrapper<StudentCourseTeacherScores> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dzx_student_number", studentNumber)
                   .eq("dzx_academic_year", academicYear);

        List<StudentCourseTeacherScores> studentScores = this.list(queryWrapper);

        if (studentScores.isEmpty()) {
            return null;
        }

        // 2. 计算平均绩点（加权平均）
        BigDecimal totalGradePoints = BigDecimal.ZERO;
        int totalCredits = 0;
        int earnedCredits = 0;

        for (StudentCourseTeacherScores score : studentScores) {
            if (score.getScore() != null && score.getCourseCredits() != null) {
                // 将分数转换为绩点（假设：90-100=4.0, 80-89=3.0, 70-79=2.0, 60-69=1.0, <60=0.0）
                BigDecimal gradePoint = convertScoreToGradePoint(score.getScore());
                totalGradePoints = totalGradePoints.add(gradePoint.multiply(new BigDecimal(score.getCourseCredits())));
                totalCredits += score.getCourseCredits();

                // 如果通过了这门课程，增加已获得学分
                if ("是".equals(score.getIsPassed()) || "通过".equals(score.getIsPassed())) {
                    earnedCredits += score.getCourseCredits();
                }
            }
        }

        BigDecimal averageGpa = totalCredits > 0 ?
            totalGradePoints.divide(new BigDecimal(totalCredits), 2, RoundingMode.HALF_UP) :
            BigDecimal.ZERO;

        // 3. 计算排名 - 获取所有学生在该学年的GPA并排序
        int ranking = calculateRanking(studentNumber, academicYear, averageGpa);

        // 4. 构建返回结果
        ScoreStatistics result = new ScoreStatistics();
        StudentCourseTeacherScores firstRecord = studentScores.get(0);

        result.setStudentNumber(firstRecord.getStudentNumber());
        result.setStudentName(firstRecord.getStudentName());
        result.setStudentGender(firstRecord.getStudentGender());
        result.setStudentAge(firstRecord.getStudentAge());
        result.setClassName(firstRecord.getClassName());
        result.setDepartmentName(firstRecord.getDepartmentName());
        result.setRegionName(firstRecord.getRegionName());
        result.setAverageGpa(averageGpa);
        result.setRanking(ranking);
        result.setAcademicYear(academicYear);
        result.setTotalCredits(totalCredits);
        result.setEarnedCredits(earnedCredits);

        return result;
    }

    /**
     * 将分数转换为绩点
     * 计算公式：((分数 - 50) * 5) / 50
     */
    private BigDecimal convertScoreToGradePoint(BigDecimal score) {
        // 使用新的GPA计算公式：((分数 - 50) * 5) / 50
        BigDecimal result = score.subtract(new BigDecimal("50"))
                                .multiply(new BigDecimal("5"))
                                .divide(new BigDecimal("50"), 2, RoundingMode.HALF_UP);

        // 确保绩点不为负数，最低为0
        return result.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : result;
    }

    /**
     * 计算学生在指定学年的排名
     */
    private int calculateRanking(String studentNumber, String academicYear, BigDecimal studentGpa) {
        // 获取该学年所有学生的成绩数据
        QueryWrapper<StudentCourseTeacherScores> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dzx_academic_year", academicYear)
                   .select("DISTINCT dzx_student_number");

        List<StudentCourseTeacherScores> allStudents = this.list(queryWrapper);

        int ranking = 1;
        for (StudentCourseTeacherScores student : allStudents) {
            if (!student.getStudentNumber().equals(studentNumber)) {
                // 计算其他学生的GPA
                QueryWrapper<StudentCourseTeacherScores> otherStudentQuery = new QueryWrapper<>();
                otherStudentQuery.eq("dzx_student_number", student.getStudentNumber())
                               .eq("dzx_academic_year", academicYear);

                List<StudentCourseTeacherScores> otherStudentScores = this.list(otherStudentQuery);
                BigDecimal otherGpa = calculateGpaForStudent(otherStudentScores);

                if (otherGpa.compareTo(studentGpa) > 0) {
                    ranking++;
                }
            }
        }
        return ranking;
    }

    /**
     * 计算指定学生成绩列表的GPA
     */
    private BigDecimal calculateGpaForStudent(List<StudentCourseTeacherScores> scores) {
        BigDecimal totalGradePoints = BigDecimal.ZERO;
        int totalCredits = 0;

        for (StudentCourseTeacherScores score : scores) {
            if (score.getScore() != null && score.getCourseCredits() != null) {
                BigDecimal gradePoint = convertScoreToGradePoint(score.getScore());
                totalGradePoints = totalGradePoints.add(gradePoint.multiply(new BigDecimal(score.getCourseCredits())));
                totalCredits += score.getCourseCredits();
            }
        }

        return totalCredits > 0 ?
            totalGradePoints.divide(new BigDecimal(totalCredits), 2, RoundingMode.HALF_UP) :
            BigDecimal.ZERO;
    }
}
