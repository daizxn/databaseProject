-- 创建学生学年成绩统计视图
CREATE VIEW view_student_yearly_statistics
(
    dzx_student_number,
    dzx_student_name,
    dzx_class_name,
    dzx_department_name,
    dzx_academic_year,
    dzx_average_score,
    dzx_average_gpa,
    dzx_total_credits,
    dzx_earned_credits,
    dzx_total_courses,
    dzx_passed_courses,
    dzx_excellent_courses
)
AS
SELECT
    v.dzx_student_number,
    v.dzx_student_name,
    v.dzx_class_name,
    v.dzx_department_name,
    v.dzx_academic_year,
    -- 平均分数
    ROUND(AVG(v.dzx_score), 2) AS dzx_average_score,
    -- 平均绩点 = (分数-50)*5/50，按学分加权平均
    ROUND(
        SUM(
            CASE
                WHEN v.dzx_score IS NOT NULL AND v.dzx_course_credits IS NOT NULL THEN
                    CASE
                        WHEN v.dzx_score >= 50 THEN ((v.dzx_score - 50) * 5 / 50) * v.dzx_course_credits
                        ELSE 0
                    END
                ELSE 0
            END
        ) /
        NULLIF(SUM(
            CASE
                WHEN v.dzx_score IS NOT NULL AND v.dzx_course_credits IS NOT NULL THEN v.dzx_course_credits
                ELSE 0
            END
        ), 0), 2
    ) AS dzx_average_gpa,
    -- 总学分
    SUM(
        CASE
            WHEN v.dzx_course_credits IS NOT NULL THEN v.dzx_course_credits
            ELSE 0
        END
    ) AS dzx_total_credits,
    -- 已获得学分（及格课程的学分）
    SUM(
        CASE
            WHEN v.dzx_score >= 60 AND v.dzx_course_credits IS NOT NULL THEN v.dzx_course_credits
            ELSE 0
        END
    ) AS dzx_earned_credits,
    -- 课程总数
    COUNT(*) AS dzx_total_courses,
    -- 通过课程数（分数>=60）
    SUM(
        CASE
            WHEN v.dzx_score >= 60 THEN 1
            ELSE 0
        END
    ) AS dzx_passed_courses,
    -- 优秀课程数（分数>=85）
    SUM(
        CASE
            WHEN v.dzx_score >= 85 THEN 1
            ELSE 0
        END
    ) AS dzx_excellent_courses
FROM view_student_course_teacher_scores v
WHERE v.dzx_score IS NOT NULL
GROUP BY
    v.dzx_student_number,
    v.dzx_student_name,
    v.dzx_class_name,
    v.dzx_department_name,
    v.dzx_academic_year
ORDER BY
    v.dzx_academic_year DESC,
    v.dzx_department_name,
    v.dzx_class_name,
    v.dzx_student_number;

-- 设置视图所有者
ALTER TABLE view_student_yearly_statistics OWNER TO db_user30;
