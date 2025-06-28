package org.example.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

/**
 * 
 * @TableName daizx_scores
 */
@TableName(value ="daizx_scores")
@Data
public class Scores {
    /**
     * 
     */
    @TableField(value = "dzx_student_number")
    private String studentNumber;

    /**
     * 
     */
    @TableField(value = "dzx_course_number")
    private String courseNumber;

    /**
     * 
     */
    @TableField(value = "dzx_academic_year")
    private String academicYear;

    /**
     * 
     */
    @TableField(value = "dzx_semester")
    private Integer semester;

    /**
     * 
     */
    @TableField(value = "dzx_score")
    private BigDecimal score;

    /**
     * 
     */
    @TableField(value = "dzx_teacher_number")
    private String teacherNumber;

    /**
     * 
     */
    @TableField(value = "dzx_grade_level")
    private String gradeLevel;

    /**
     * 
     */
    @TableField(value = "dzx_exam_date")
    private LocalDate examDate;

    /**
     * 
     */
    @TableField(value = "dzx_score_status")
    private String scoreStatus;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Scores other = (Scores) that;
        return (this.getStudentNumber() == null ? other.getStudentNumber() == null : this.getStudentNumber().equals(other.getStudentNumber()))
            && (this.getCourseNumber() == null ? other.getCourseNumber() == null : this.getCourseNumber().equals(other.getCourseNumber()))
            && (this.getAcademicYear() == null ? other.getAcademicYear() == null : this.getAcademicYear().equals(other.getAcademicYear()))
            && (this.getSemester() == null ? other.getSemester() == null : this.getSemester().equals(other.getSemester()))
            && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()))
            && (this.getTeacherNumber() == null ? other.getTeacherNumber() == null : this.getTeacherNumber().equals(other.getTeacherNumber()))
            && (this.getGradeLevel() == null ? other.getGradeLevel() == null : this.getGradeLevel().equals(other.getGradeLevel()))
            && (this.getExamDate() == null ? other.getExamDate() == null : this.getExamDate().equals(other.getExamDate()))
            && (this.getScoreStatus() == null ? other.getScoreStatus() == null : this.getScoreStatus().equals(other.getScoreStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStudentNumber() == null) ? 0 : getStudentNumber().hashCode());
        result = prime * result + ((getCourseNumber() == null) ? 0 : getCourseNumber().hashCode());
        result = prime * result + ((getAcademicYear() == null) ? 0 : getAcademicYear().hashCode());
        result = prime * result + ((getSemester() == null) ? 0 : getSemester().hashCode());
        result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
        result = prime * result + ((getTeacherNumber() == null) ? 0 : getTeacherNumber().hashCode());
        result = prime * result + ((getGradeLevel() == null) ? 0 : getGradeLevel().hashCode());
        result = prime * result + ((getExamDate() == null) ? 0 : getExamDate().hashCode());
        result = prime * result + ((getScoreStatus() == null) ? 0 : getScoreStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", studentNumber=").append(studentNumber);
        sb.append(", courseNumber=").append(courseNumber);
        sb.append(", academicYear=").append(academicYear);
        sb.append(", semester=").append(semester);
        sb.append(", score=").append(score);
        sb.append(", teacherNumber=").append(teacherNumber);
        sb.append(", gradeLevel=").append(gradeLevel);
        sb.append(", examDate=").append(examDate);
        sb.append(", scoreStatus=").append(scoreStatus);
        sb.append("]");
        return sb.toString();
    }
}