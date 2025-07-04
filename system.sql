create table if not exists daizx_users (
    id serial primary key,
    username varchar(50) not null unique,
    password varchar(100) not null,
    ref_id varchar(20),
    enable boolean default true
) with (orientation = row, compression = no);
alter table daizx_users owner to db_user30;
create table if not exists daizx_roles (
    id serial primary key,
    name varchar(50) not null unique,
    description text
) with (orientation = row, compression = no);
alter table daizx_roles owner to db_user30;
create table if not exists daizx_permissions (
    id serial primary key,
    permission_code varchar(100) not null unique,
    description text
) with (orientation = row, compression = no);
alter table daizx_permissions owner to db_user30;
create table if not exists daizx_user_roles (
    user_id integer not null references daizx_users on delete cascade,
    role_id integer not null references daizx_roles on delete cascade,
    primary key (user_id, role_id)
) with (orientation = row, compression = no);
alter table daizx_user_roles owner to db_user30;
create table if not exists daizx_role_permissions (
    role_id integer not null references daizx_roles on delete cascade,
    permission_id integer not null references daizx_permissions on delete cascade,
    primary key (role_id, permission_id)
) with (orientation = row, compression = no);
alter table daizx_role_permissions owner to db_user30;
create table if not exists daizx_regions (
    dzx_region_id serial primary key,
    dzx_region_name varchar(50) not null unique
) with (orientation = row, compression = no);
alter table daizx_regions owner to db_user30;
create table if not exists daizx_departments (
    dzx_department_id serial primary key,
    dzx_department_name varchar(100) not null unique
) with (orientation = row, compression = no);
alter table daizx_departments owner to db_user30;
create table if not exists daizx_classes (
    dzx_class_id serial primary key,
    dzx_class_name varchar(50) not null,
    dzx_department_id integer references daizx_departments on delete cascade,
    unique (dzx_class_name, dzx_department_id)
) with (orientation = row, compression = no);
alter table daizx_classes owner to db_user30;
create table if not exists daizx_students (
    dzx_student_number varchar(20) not null primary key,
    dzx_student_name varchar(50) not null,
    dzx_student_gender varchar(10) constraint daizx_students_dzx_student_gender_check check (
        (dzx_student_gender)::text = ANY (
            (
                ARRAY ['男'::character varying, '女'::character varying, '其他'::character varying]
            )::text []
        )
    ),
    dzx_student_age integer constraint daizx_students_dzx_student_age_check check (
        (dzx_student_age >= 16)
        AND (dzx_student_age <= 60)
    ),
    dzx_region_id integer references daizx_regions on delete cascade,
    dzx_student_total_credits integer default 0 constraint daizx_students_dzx_student_total_credits_check check (dzx_student_total_credits >= 0),
    dzx_class_id integer references daizx_classes on delete cascade,
    dzx_enrollment_date date default text_date('now'::text),
    dzx_student_status varchar(20) default '在读'::character varying constraint daizx_students_dzx_student_status_check check (
        (dzx_student_status)::text = ANY (
            (
                ARRAY ['在读'::character varying, '毕业'::character varying, '休学'::character varying, '退学'::character varying]
            )::text []
        )
    )
) with (orientation = row, compression = no);
alter table daizx_students owner to db_user30;
create table if not exists daizx_teachers (
    dzx_teacher_number varchar(20) not null primary key,
    dzx_teacher_name varchar(50) not null,
    dzx_teacher_gender varchar(10) constraint daizx_teachers_dzx_teacher_gender_check check (
        (dzx_teacher_gender)::text = ANY (
            (
                ARRAY ['男'::character varying, '女'::character varying, '其他'::character varying]
            )::text []
        )
    ),
    dzx_teacher_age integer constraint daizx_teachers_dzx_teacher_age_check check (
        (dzx_teacher_age >= 22)
        AND (dzx_teacher_age <= 70)
    ),
    dzx_teacher_title varchar(50) constraint daizx_teachers_dzx_teacher_title_check check (
        (dzx_teacher_title)::text = ANY (
            (
                ARRAY ['助教'::character varying, '讲师'::character varying, '副教授'::character varying, '教授'::character varying, '研究员'::character varying, '高级工程师'::character varying]
            )::text []
        )
    ),
    dzx_teacher_phone varchar(20) constraint daizx_teachers_dzx_teacher_phone_check check (
        (dzx_teacher_phone)::text ~ '^[0-9\-+() ]+$'::text
    ),
    dzx_hire_date date default text_date('now'::text),
    dzx_teacher_status varchar(20) default '在职'::character varying constraint daizx_teachers_dzx_teacher_status_check check (
        (dzx_teacher_status)::text = ANY (
            (
                ARRAY ['在职'::character varying, '退休'::character varying, '离职'::character varying]
            )::text []
        )
    ),
    dzx_teacher_email varchar(50) constraint teacher_email check (
        (dzx_teacher_email)::text ~ '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'::text
    )
) with (orientation = row, compression = no);
alter table daizx_teachers owner to db_user30;
create table if not exists daizx_courses (
    dzx_course_number varchar(20) not null primary key,
    dzx_course_name varchar(100) not null,
    dzx_teacher_number varchar(20) references daizx_teachers on delete
    set null,
        dzx_academic_year varchar(9) not null,
        dzx_semester integer not null constraint daizx_courses_dzx_semester_check check (dzx_semester = ANY (ARRAY [1, 2, 3])),
        dzx_course_hours integer constraint daizx_courses_dzx_course_hours_check check (
            (dzx_course_hours > 0)
            AND (dzx_course_hours <= 200)
        ),
        dzx_course_type varchar(20) constraint daizx_courses_dzx_course_type_check check (
            (dzx_course_type)::text = ANY (
                (
                    ARRAY ['必修'::character varying, '选修'::character varying, '专业必修'::character varying, '专业选修'::character varying, '通识课'::character varying]
                )::text []
            )
        ),
        dzx_course_credits integer constraint daizx_courses_dzx_course_credits_check check (
            (dzx_course_credits > 0)
            AND (dzx_course_credits <= 10)
        ),
        dzx_class_id integer references daizx_classes on delete cascade,
        dzx_course_status varchar(20) default '开设'::character varying constraint daizx_courses_dzx_course_status_check check (
            (dzx_course_status)::text = ANY (
                (
                    ARRAY ['开设'::character varying, '停开'::character varying, '暂停'::character varying]
                )::text []
            )
        ),
        dzx_course_exam_type varchar(20) constraint daizxn_exam_type_check check (
            (dzx_course_exam_type)::text = ANY (
                ARRAY [('考试'::character varying)::text, ('考察'::character varying)::text]
            )
        )
) with (orientation = row, compression = no);
alter table daizx_courses owner to db_user30;
create table if not exists daizx_scores (
    dzx_student_number varchar(20) not null references daizx_students on delete cascade,
    dzx_course_number varchar(20) not null references daizx_courses on delete cascade,
    dzx_academic_year varchar(9) not null,
    dzx_semester integer not null constraint daizx_scores_dzx_semester_check check (dzx_semester = ANY (ARRAY [1, 2, 3])),
    dzx_score numeric(5, 2) constraint daizx_scores_dzx_score_check check (
        (dzx_score >= (0)::numeric)
        AND (dzx_score <= (100)::numeric)
    ),
    dzx_teacher_number varchar(20) references daizx_teachers on delete
    set null,
        dzx_grade_level varchar(10) constraint daizx_scores_dzx_grade_level_check check (
            (dzx_grade_level)::text = ANY (
                ARRAY [('优秀'::character varying)::text, ('良好'::character varying)::text, ('中等'::character varying)::text, ('及格'::character varying)::text, ('不及格'::character varying)::text, ('未评分'::character varying)::text]
            )
        ),
        dzx_exam_date date,
        dzx_score_status varchar(20) default '正常'::character varying constraint daizx_scores_dzx_score_status_check check (
            (dzx_score_status)::text = ANY (
                (
                    ARRAY ['正常'::character varying, '缓考'::character varying, '重修'::character varying, '作弊'::character varying]
                )::text []
            )
        ),
        primary key (
            dzx_student_number,
            dzx_course_number,
            dzx_academic_year,
            dzx_semester
        )
) with (orientation = row, compression = no);
alter table daizx_scores owner to db_user30;
create or replace view view_region_students (
        dzx_region_id,
        dzx_region_name,
        dzx_student_number,
        dzx_student_name,
        dzx_student_gender,
        dzx_student_age,
        dzx_student_total_credits,
        dzx_class_name,
        dzx_department_name,
        dzx_enrollment_date,
        dzx_student_status
    ) as
SELECT r.dzx_region_id,
    r.dzx_region_name,
    s.dzx_student_number,
    s.dzx_student_name,
    s.dzx_student_gender,
    s.dzx_student_age,
    s.dzx_student_total_credits,
    c.dzx_class_name,
    d.dzx_department_name,
    s.dzx_enrollment_date,
    s.dzx_student_status
FROM daizx_regions r
    LEFT JOIN daizx_students s ON r.dzx_region_id = s.dzx_region_id
    LEFT JOIN daizx_classes c ON s.dzx_class_id = c.dzx_class_id
    LEFT JOIN daizx_departments d ON c.dzx_department_id = d.dzx_department_id;
alter table view_region_students owner to db_user30;
create or replace view view_student_courses (
        dzx_student_number,
        dzx_student_name,
        dzx_student_gender,
        dzx_class_name,
        dzx_department_name,
        dzx_course_number,
        dzx_course_name,
        dzx_course_type,
        dzx_course_credits,
        dzx_course_hours,
        dzx_academic_year,
        dzx_semester,
        dzx_course_status,
        dzx_course_exam_type
    ) as
SELECT s.dzx_student_number,
    s.dzx_student_name,
    s.dzx_student_gender,
    c.dzx_class_name,
    d.dzx_department_name,
    co.dzx_course_number,
    co.dzx_course_name,
    co.dzx_course_type,
    co.dzx_course_credits,
    co.dzx_course_hours,
    co.dzx_academic_year,
    co.dzx_semester,
    co.dzx_course_status,
    co.dzx_course_exam_type
FROM daizx_students s
    JOIN daizx_classes c ON s.dzx_class_id = c.dzx_class_id
    JOIN daizx_departments d ON c.dzx_department_id = d.dzx_department_id
    JOIN daizx_courses co ON s.dzx_class_id = co.dzx_class_id;
alter table view_student_courses owner to db_user30;
create or replace view view_teacher_courses (
        dzx_teacher_number,
        dzx_teacher_name,
        dzx_teacher_gender,
        dzx_teacher_title,
        dzx_teacher_status,
        dzx_course_number,
        dzx_course_name,
        dzx_course_type,
        dzx_course_credits,
        dzx_course_hours,
        dzx_academic_year,
        dzx_semester,
        dzx_class_name,
        dzx_department_name,
        dzx_course_status,
        dzx_course_exam_type
    ) as
SELECT t.dzx_teacher_number,
    t.dzx_teacher_name,
    t.dzx_teacher_gender,
    t.dzx_teacher_title,
    t.dzx_teacher_status,
    co.dzx_course_number,
    co.dzx_course_name,
    co.dzx_course_type,
    co.dzx_course_credits,
    co.dzx_course_hours,
    co.dzx_academic_year,
    co.dzx_semester,
    c.dzx_class_name,
    d.dzx_department_name,
    co.dzx_course_status,
    co.dzx_course_exam_type
FROM daizx_teachers t
    LEFT JOIN daizx_courses co ON t.dzx_teacher_number::text = co.dzx_teacher_number::text
    LEFT JOIN daizx_classes c ON co.dzx_class_id = c.dzx_class_id
    LEFT JOIN daizx_departments d ON c.dzx_department_id = d.dzx_department_id;
alter table view_teacher_courses owner to db_user30;
create or replace view view_student_course_teacher_scores (
        dzx_student_number,
        dzx_student_name,
        dzx_student_gender,
        dzx_student_age,
        dzx_class_name,
        dzx_department_name,
        dzx_region_name,
        dzx_course_number,
        dzx_course_name,
        dzx_course_type,
        dzx_course_credits,
        dzx_course_hours,
        dzx_teacher_number,
        dzx_teacher_name,
        dzx_teacher_title,
        dzx_semester,
        dzx_score,
        dzx_grade_level,
        dzx_exam_date,
        dzx_score_status,
        dzx_is_passed
    ) as
SELECT s.dzx_student_number,
    s.dzx_student_name,
    s.dzx_student_gender,
    s.dzx_student_age,
    cl.dzx_class_name,
    d.dzx_department_name,
    r.dzx_region_name,
    co.dzx_course_number,
    co.dzx_course_name,
    co.dzx_course_type,
    co.dzx_course_credits,
    co.dzx_course_hours,
    t.dzx_teacher_number,
    t.dzx_teacher_name,
    t.dzx_teacher_title,
    sc.dzx_semester,
    sc.dzx_score,
    sc.dzx_grade_level,
    sc.dzx_exam_date,
    sc.dzx_score_status,
    CASE
        WHEN sc.dzx_score >= 60::numeric THEN '是'::text
        ELSE '否'::text
    END AS dzx_is_passed
FROM daizx_scores sc
    LEFT JOIN daizx_students s ON sc.dzx_student_number::text = s.dzx_student_number::text
    LEFT JOIN daizx_classes cl ON s.dzx_class_id = cl.dzx_class_id
    LEFT JOIN daizx_departments d ON cl.dzx_department_id = d.dzx_department_id
    LEFT JOIN daizx_regions r ON s.dzx_region_id = r.dzx_region_id
    LEFT JOIN daizx_courses co ON sc.dzx_course_number::text = co.dzx_course_number::text
    LEFT JOIN daizx_teachers t ON sc.dzx_teacher_number::text = t.dzx_teacher_number::text;
alter table view_student_course_teacher_scores owner to db_user30;
create or replace view view_student_full_info (
        dzx_student_number,
        dzx_student_name,
        dzx_student_gender,
        dzx_student_age,
        dzx_student_total_credits,
        dzx_enrollment_date,
        dzx_student_status,
        dzx_region_name,
        dzx_class_name,
        dzx_department_name,
        gpa,
        weighted_gpa,
        department_gpa_rank,
        department_total_students,
        department_rank_percentage
    ) as WITH student_gpa AS (
        SELECT s.dzx_student_number,
            round(
                CASE
                    WHEN count(sc.dzx_score) = 0 THEN 0::numeric
                    ELSE avg(
                        CASE
                            WHEN sc.dzx_score < 60::numeric THEN 0.0
                            ELSE (sc.dzx_score - 50::numeric) * 5.0 / 50.0
                        END
                    )
                END,
                2
            ) AS gpa,
            round(
                CASE
                    WHEN sum(co.dzx_course_credits) = 0 THEN 0::numeric
                    ELSE sum(
                        CASE
                            WHEN sc.dzx_score < 60::numeric THEN 0.0
                            ELSE (sc.dzx_score - 50::numeric) * 5.0 / 50.0
                        END * co.dzx_course_credits::numeric
                    ) / sum(co.dzx_course_credits)::numeric
                END,
                2
            ) AS weighted_gpa
        FROM daizx_students s
            LEFT JOIN daizx_scores sc ON s.dzx_student_number::text = sc.dzx_student_number::text
            LEFT JOIN daizx_courses co ON sc.dzx_course_number::text = co.dzx_course_number::text
        GROUP BY s.dzx_student_number
    ),
    department_ranking AS (
        SELECT s.dzx_student_number,
            c.dzx_department_id,
            sg.gpa,
            sg.weighted_gpa,
            row_number() OVER (
                PARTITION BY c.dzx_department_id
                ORDER BY sg.weighted_gpa DESC,
                    sg.gpa DESC
            ) AS department_gpa_rank,
            count(*) OVER (PARTITION BY c.dzx_department_id) AS department_total_students
        FROM daizx_students s
            LEFT JOIN daizx_classes c ON s.dzx_class_id = c.dzx_class_id
            LEFT JOIN student_gpa sg ON s.dzx_student_number::text = sg.dzx_student_number::text
    )
SELECT s.dzx_student_number,
    s.dzx_student_name,
    s.dzx_student_gender,
    s.dzx_student_age,
    s.dzx_student_total_credits,
    s.dzx_enrollment_date,
    s.dzx_student_status,
    r.dzx_region_name,
    c.dzx_class_name,
    d.dzx_department_name,
    dr.gpa,
    dr.weighted_gpa,
    dr.department_gpa_rank,
    dr.department_total_students,
    round(
        dr.department_gpa_rank::numeric / dr.department_total_students::numeric * 100::numeric,
        1
    ) AS department_rank_percentage
FROM daizx_students s
    LEFT JOIN daizx_regions r ON s.dzx_region_id = r.dzx_region_id
    LEFT JOIN daizx_classes c ON s.dzx_class_id = c.dzx_class_id
    LEFT JOIN daizx_departments d ON c.dzx_department_id = d.dzx_department_id
    LEFT JOIN department_ranking dr ON s.dzx_student_number::text = dr.dzx_student_number::text;
alter table view_student_full_info owner to db_user30;
create or replace function update_grade_level() returns trigger language plpgsql as $$ BEGIN -- 根据分数自动设置等级
    IF NEW.dzx_score >= 90 THEN NEW.dzx_grade_level := '优秀';
ELSIF NEW.dzx_score >= 80 THEN NEW.dzx_grade_level := '良好';
ELSIF NEW.dzx_score >= 70 THEN NEW.dzx_grade_level := '中等';
ELSIF NEW.dzx_score >= 60 THEN NEW.dzx_grade_level := '及格';
ELSE NEW.dzx_grade_level := '不及格';
END IF;
RETURN NEW;
END;
$$;
alter function update_grade_level() owner to db_user30;
create trigger trg_update_grade_level before
insert
    or
update of dzx_score on daizx_scores for each row execute procedure update_grade_level();
create or replace function update_credit() returns trigger language plpgsql as $$ BEGIN
UPDATE daizx_Students
SET dzx_student_total_credits = dzx_student_total_credits + (
        SELECT dzx_course_credits
        FROM daizx_Courses
        WHERE dzx_course_number = NEW.dzx_course_number
    )
WHERE dzx_student_number = NEW.dzx_student_number
    AND NEW.dzx_score >= 60;
-- 只有及格才加学分
RETURN NEW;
END;
$$;
alter function update_credit() owner to db_user30;
create trigger trg_update_credit
after
insert on daizx_scores for each row execute procedure update_credit();
create or replace function get_student_avg(stu_id character varying) returns void language plpgsql as $$
DECLARE course_name VARCHAR(100);
score NUMERIC(5, 2);
avg_score NUMERIC(5, 2);
CURSOR C IS
SELECT c.dzx_course_name,
    s.dzx_score,
    (
        SELECT AVG(s2.dzx_score)
        FROM daizx_Scores s2
        WHERE s2.dzx_student_number = stu_id
    )
FROM daizx_Scores s
    JOIN daizx_Courses c ON s.dzx_course_number = c.dzx_course_number
WHERE s.dzx_student_number = stu_id;
BEGIN OPEN C;
LOOP FETCH C INTO course_name,
score,
avg_score;
EXIT
WHEN C %NOTFOUND;
RAISE INFO 'Course: %, Score: %, AvgScore: %',
course_name,
score,
avg_score;
END LOOP;
CLOSE C;
END $$;
alter function get_student_avg(varchar) owner to db_user30;
create or replace function get_students_count_by_region(region_id_param integer) returns void language plpgsql as $$
DECLARE region_name VARCHAR (50);
student_count BIGINT;
CURSOR C IS
SELECT r.dzx_region_name,
    COUNT(s.dzx_student_number)
FROM daizx_Regions r
    LEFT JOIN daizx_Students s ON r.dzx_region_id = s.dzx_region_id
WHERE r.dzx_region_id = region_id_param
GROUP BY r.dzx_region_id,
    r.dzx_region_name;
BEGIN OPEN C;
LOOP FETCH C INTO region_name,
student_count;
EXIT
WHEN C %NOTFOUND;
RAISE info 'Region: % , StudentCount: %',
region_name,
student_count;
END LOOP;
CLOSE C;
END $$;
alter function get_students_count_by_region(integer) owner to db_user30;
create or replace function get_students_by_region(region_id_param integer) returns void language plpgsql as $$
DECLARE region_name VARCHAR (50);
student_no VARCHAR(20);
student_name VARCHAR(50);
student_gender VARCHAR(10);
student_age INT;
student_credits INT;
class_name VARCHAR(50);
department_name VARCHAR(100);
CURSOR C IS
SELECT r.dzx_region_name,
    s.dzx_student_number,
    s.dzx_student_name,
    s.dzx_student_gender,
    s.dzx_student_age,
    s.dzx_student_total_credits,
    c.dzx_class_name,
    d.dzx_department_name
FROM daizx_Regions r
    LEFT JOIN daizx_Students s ON r.dzx_region_id = s.dzx_region_id
    LEFT JOIN daizx_Classes c ON s.dzx_class_id = c.dzx_class_id
    LEFT JOIN daizx_Departments d ON c.dzx_department_id = d.dzx_department_id
WHERE r.dzx_region_id = region_id_param
ORDER BY s.dzx_student_number;
BEGIN OPEN C;
LOOP FETCH C INTO region_name,
student_no,
student_name,
student_gender,
student_age,
student_credits,
class_name,
department_name;
EXIT
WHEN C %NOTFOUND;
RAISE info 'Region: % , StudentNo: % , StudentName: % , Gender: % , Age: % , Credits: % , Class: % , Department: %',
region_name,
student_no,
student_name,
student_gender,
student_age,
student_credits,
class_name,
department_name;
END LOOP;
CLOSE C;
END $$;
alter function get_students_by_region(integer) owner to db_user30;
create or replace function get_all_regions_student_stats() returns void language plpgsql as $$
DECLARE region_id INT;
region_name VARCHAR(50);
student_count BIGINT;
avg_age NUMERIC(5, 2);
avg_credit NUMERIC(5, 2);
CURSOR C IS
SELECT r.dzx_region_id,
    r.dzx_region_name,
    COUNT(s.dzx_student_number),
    AVG(s.dzx_student_age::NUMERIC),
    AVG(s.dzx_student_total_credits::NUMERIC)
FROM daizx_Regions r
    LEFT JOIN daizx_Students s ON r.dzx_region_id = s.dzx_region_id
GROUP BY r.dzx_region_id,
    r.dzx_region_name
ORDER BY r.dzx_region_id;
BEGIN OPEN C;
LOOP FETCH C INTO region_id,
region_name,
student_count,
avg_age,
avg_credit;
EXIT
WHEN C %NOTFOUND;
RAISE info 'RegionID: % , RegionName: % , StudentCount: % , AvgAge: % , AvgCredit: %',
region_id,
region_name,
student_count,
avg_age,
avg_credit;
END LOOP;
CLOSE C;
END $$;
alter function get_all_regions_student_stats() owner to db_user30;
create or replace function update_student_score(
        teacher_no character varying,
        student_no character varying,
        course_no character varying,
        academic_year character varying,
        semester integer,
        new_score numeric
    ) returns TABLE(
        success boolean,
        message text,
        old_score numeric,
        credit_change integer
    ) language plpgsql as $$
DECLARE old_score_value NUMERIC;
course_credit INT;
old_passed BOOLEAN := FALSE;
new_passed BOOLEAN := FALSE;
credit_delta INT := 0;
BEGIN -- 验证教师是否有权限修改此课程成绩
IF NOT EXISTS (
    SELECT 1
    FROM daizx_Courses
    WHERE dzx_course_number = course_no
        AND dzx_teacher_number = teacher_no
) THEN RETURN QUERY
SELECT FALSE,
    '错误：您没有权限修改此课程的成绩',
    NULL::NUMERIC,
    0;
RETURN;
END IF;
-- 验证成绩记录是否存在
IF NOT EXISTS (
    SELECT 1
    FROM daizx_Scores
    WHERE dzx_student_number = student_no
        AND dzx_course_number = course_no
        AND dzx_academic_year = academic_year
        AND dzx_semester = semester
) THEN RETURN QUERY
SELECT FALSE,
    '错误：找不到对应的成绩记录',
    NULL::NUMERIC,
    0;
RETURN;
END IF;
-- 验证新成绩范围
IF new_score < 0
OR new_score > 100 THEN RETURN QUERY
SELECT FALSE,
    '错误：成绩必须在0-100之间',
    NULL::NUMERIC,
    0;
RETURN;
END IF;
-- 获取原成绩和课程学分
SELECT dzx_score INTO old_score_value
FROM daizx_Scores
WHERE dzx_student_number = student_no
    AND dzx_course_number = course_no
    AND dzx_academic_year = academic_year
    AND dzx_semester = semester;
SELECT dzx_course_credits INTO course_credit
FROM daizx_Courses
WHERE dzx_course_number = course_no;
-- 判断原成绩和新成绩是否及格（60分及以上为及格）
old_passed := (old_score_value >= 60);
new_passed := (new_score >= 60);
-- 计算学分变化
IF old_passed
AND NOT new_passed THEN -- 原来及格，现在不及格：减少学分
credit_delta := - course_credit;
ELSIF NOT old_passed
AND new_passed THEN -- 原来不及格，现在及格：增加学分
credit_delta := course_credit;
ELSE -- 都及格或都不及格：学分不变
credit_delta := 0;
END IF;
-- 更新成绩
UPDATE daizx_Scores
SET dzx_score = new_score
WHERE dzx_student_number = student_no
    AND dzx_course_number = course_no
    AND dzx_academic_year = academic_year
    AND dzx_semester = semester;
-- 更新学生学分
IF credit_delta != 0 THEN
UPDATE daizx_Students
SET dzx_student_total_credits = dzx_student_total_credits + credit_delta
WHERE dzx_student_number = student_no;
END IF;
-- 返回成功信息
RETURN QUERY
SELECT TRUE,
    FORMAT(
        '成功：学生 %s 的 %s 课程成绩已从 %.2f 修改为 %.2f，学分变化：%s',
        student_no,
        course_no,
        old_score_value,
        new_score,
        CASE
            WHEN credit_delta > 0 THEN '+' || credit_delta
            WHEN credit_delta < 0 THEN credit_delta::TEXT
            ELSE '无变化'
        END
    ),
    old_score_value,
    credit_delta;
END;
$$;
alter function update_student_score(
    varchar,
    varchar,
    varchar,
    varchar,
    integer,
    numeric
) owner to db_user30;
create or replace function get_teacher_course_scores(
        p_teacher_no character varying,
        p_course_no character varying DEFAULT NULL::character varying,
        p_academic_year character varying DEFAULT NULL::character varying,
        p_semester integer DEFAULT NULL::integer
    ) returns TABLE(
        course_no character varying,
        course_name character varying,
        academic_year character varying,
        semester integer,
        student_no character varying,
        student_name character varying,
        score numeric,
        grade_level character varying,
        is_passed boolean
    ) language plpgsql as $$ BEGIN RETURN QUERY
SELECT c.dzx_course_number,
    c.dzx_course_name,
    s.dzx_academic_year,
    s.dzx_semester,
    s.dzx_student_number,
    st.dzx_student_name,
    s.dzx_score,
    s.dzx_grade_level,
    (s.dzx_score >= 60) as is_passed
FROM daizx_Courses c
    JOIN daizx_Scores s ON c.dzx_course_number = s.dzx_course_number
    JOIN daizx_Students st ON s.dzx_student_number = st.dzx_student_number
WHERE c.dzx_teacher_number = p_teacher_no
    AND (
        p_course_no IS NULL
        OR c.dzx_course_number = p_course_no
    )
    AND (
        p_academic_year IS NULL
        OR s.dzx_academic_year = p_academic_year
    )
    AND (
        p_semester IS NULL
        OR s.dzx_semester = p_semester
    )
ORDER BY c.dzx_course_number,
    s.dzx_academic_year,
    s.dzx_semester,
    s.dzx_student_number;
END;
$$;
alter function get_teacher_course_scores(varchar, varchar, varchar, integer) owner to db_user30;
create or replace function batch_update_scores(
        teacher_no character varying,
        course_no character varying,
        semester character varying,
        academic_year character varying,
        score_data text
    ) returns TABLE(
        success boolean,
        message text,
        processed_count integer,
        error_count integer
    ) language plpgsql as $$
DECLARE score_record RECORD;
processed INT := 0;
errors INT := 0;
result_msg TEXT := '';
BEGIN -- 验证教师权限
IF NOT EXISTS (
    SELECT 1
    FROM daizx_Courses
    WHERE dzx_course_number = course_no
        AND dzx_teacher_number = teacher_no
) THEN RETURN QUERY
SELECT FALSE,
    '错误：您没有权限修改此课程的成绩',
    0,
    0;
RETURN;
END IF;
-- 解析JSON数据并处理每条记录
FOR score_record IN
SELECT *
FROM json_to_recordset(score_data::json) AS x(student_no VARCHAR, score NUMERIC) LOOP BEGIN -- 检查成绩是否存在，存在则更新，不存在则插入
    IF EXISTS (
        SELECT 1
        FROM daizx_Scores
        WHERE dzx_student_number = score_record.student_no
            AND dzx_course_number = course_no
            AND dzx_semester = semester
            AND dzx_academic_year = academic_year
    ) THEN -- 更新现有成绩
    PERFORM update_student_score(
        teacher_no,
        score_record.student_no,
        course_no,
        semester,
        academic_year,
        score_record.score
    );
ELSE -- 插入新成绩
INSERT INTO daizx_Scores (
        dzx_student_number,
        dzx_course_number,
        dzx_academic_year,
        dzx_semester,
        dzx_score,
        dzx_teacher_number,
        dzx_exam_date
    )
VALUES (
        score_record.student_no,
        course_no,
        academic_year,
        semester,
        score_record.score,
        teacher_no,
        CURRENT_DATE
    );
-- 如果及格，增加学分
IF score_record.score >= 60 THEN
UPDATE daizx_Students
SET dzx_student_total_credits = dzx_student_total_credits + (
        SELECT dzx_course_credits
        FROM daizx_Courses
        WHERE dzx_course_number = course_no
    )
WHERE dzx_student_number = score_record.student_no;
END IF;
END IF;
processed := processed + 1;
EXCEPTION
WHEN OTHERS THEN errors := errors + 1;
result_msg := result_msg || FORMAT('学生%s处理失败；', score_record.student_no);
END;
END LOOP;
-- 返回处理结果
IF errors = 0 THEN result_msg := FORMAT('成功处理%s条成绩记录', processed);
ELSE result_msg := FORMAT(
    '处理完成：成功%s条，失败%s条。%s',
    processed,
    errors,
    result_msg
);
END IF;
RETURN QUERY
SELECT (errors = 0),
    result_msg,
    processed,
    errors;
END;
$$;
alter function batch_update_scores(varchar, varchar, varchar, varchar, text) owner to db_user30;
create or replace function proc_re(incno character varying) returns void language plpgsql as $$
DECLARE stu_Sno VARCHAR (20);
stu_Sname VARCHAR(50);
cou_name VARCHAR(100);
cou_Score DECIMAL(5, 2);
CURSOR C IS
SELECT s.dzx_student_number,
    s.dzx_student_name,
    c.dzx_course_name,
    sc.dzx_score
FROM daizx_Students s,
    daizx_Courses c,
    daizx_Scores sc
WHERE s.dzx_student_number = sc.dzx_student_number
    AND sc.dzx_course_number = c.dzx_course_number
    AND c.dzx_course_number = inCno;
BEGIN OPEN C;
LOOP FETCH C INTO stu_Sno,
stu_Sname,
cou_name,
cou_Score;
EXIT
WHEN C %NOTFOUND;
RAISE info 'Sno: % , Sname: % , Cname: % , Score: %',
stu_Sno,
stu_Sname,
cou_name,
cou_Score;
END LOOP;
CLOSE C;
END $$;
alter function proc_re(varchar) owner to db_user30;
create or replace function delete_student_scores() returns trigger language plpgsql as $$ BEGIN -- 删除该学生的所有成绩记录
DELETE FROM daizx_Scores
WHERE dzx_student_number = OLD.dzx_student_number;
-- 记录删除信息
RAISE NOTICE '已删除学生 % 的所有成绩记录',
OLD.dzx_student_name;
RETURN OLD;
END;
$$;
alter function delete_student_scores() owner to db_user30;
create trigger trg_delete_student_scores before delete on daizx_students for each row execute procedure delete_student_scores();
create or replace function delete_teacher_relations() returns trigger language plpgsql as $$ BEGIN -- 删除该教师教授课程的所有成绩记录
DELETE FROM daizx_Scores
WHERE dzx_teacher_number = OLD.dzx_teacher_number;
-- 将该教师教授的课程的教师字段设为NULL
UPDATE daizx_Courses
SET dzx_teacher_number = NULL
WHERE dzx_teacher_number = OLD.dzx_teacher_number;
-- 记录删除信息
RAISE NOTICE '已处理教师 % 相关的课程和成绩记录',
OLD.dzx_teacher_name;
RETURN OLD;
END;
$$;
alter function delete_teacher_relations() owner to db_user30;
create trigger trg_delete_teacher_relations before delete on daizx_teachers for each row execute procedure delete_teacher_relations();
create or replace function delete_course_scores() returns trigger language plpgsql as $$ BEGIN -- 删除该课程的所有成绩记录
DELETE FROM daizx_Scores
WHERE dzx_course_number = OLD.dzx_course_number;
-- 记录删除信息
RAISE NOTICE '已删除课程 % 的所有成绩记录',
OLD.dzx_course_name;
RETURN OLD;
END;
$$;
alter function delete_course_scores() owner to db_user30;
create trigger trg_delete_course_scores before delete on daizx_courses for each row execute procedure delete_course_scores();
create or replace function delete_class_relations() returns trigger language plpgsql as $$ BEGIN -- 将该班级的学生的班级字段设为NULL
UPDATE daizx_Students
SET dzx_class_id = NULL
WHERE dzx_class_id = OLD.dzx_class_id;
-- 删除该班级的所有课程（级联删除会自动处理成绩）
DELETE FROM daizx_Courses
WHERE dzx_class_id = OLD.dzx_class_id;
-- 记录删除信息
RAISE NOTICE '已处理班级 % 相关的学生和课程记录',
OLD.dzx_class_name;
RETURN OLD;
END;
$$;
alter function delete_class_relations() owner to db_user30;
create trigger trg_delete_class_relations before delete on daizx_classes for each row execute procedure delete_class_relations();
create or replace function delete_department_relations() returns trigger language plpgsql as $$ BEGIN -- 删除该专业下的所有班级（会级联删除相关的学生和课程关联）
DELETE FROM daizx_Classes
WHERE dzx_department_id = OLD.dzx_department_id;
-- 记录删除信息
RAISE NOTICE '已删除专业 % 下的所有班级及相关记录',
OLD.dzx_department_name;
RETURN OLD;
END;
$$;
alter function delete_department_relations() owner to db_user30;
create trigger trg_delete_department_relations before delete on daizx_departments for each row execute procedure delete_department_relations();
create or replace function delete_region_relations() returns trigger language plpgsql as $$ BEGIN -- 将该地区的学生的地区字段设为NULL
UPDATE daizx_Students
SET dzx_region_id = NULL
WHERE dzx_region_id = OLD.dzx_region_id;
-- 记录删除信息
RAISE NOTICE '已将地区 % 相关学生的地区信息设为空',
OLD.dzx_region_name;
RETURN OLD;
END;
$$;
alter function delete_region_relations() owner to db_user30;
create trigger trg_delete_region_relations before delete on daizx_regions for each row execute procedure delete_region_relations();
create or replace function delete_user_rbac_relations() returns trigger language plpgsql as $$ BEGIN -- 删除用户-角色关联
DELETE FROM daizx_User_Roles
WHERE user_id = OLD.id;
-- 记录删除信息
RAISE NOTICE '已清理用户 % 的RBAC权限关联',
OLD.username;
RETURN OLD;
END;
$$;
alter function delete_user_rbac_relations() owner to db_user30;
create trigger trg_delete_user_rbac_relations before delete on daizx_users for each row execute procedure delete_user_rbac_relations();
create or replace function delete_role_permissions() returns trigger language plpgsql as $$ BEGIN -- 删除角色-权限关联
DELETE FROM daizx_Role_Permissions
WHERE role_id = OLD.id;
-- 删除用户-角色关联
DELETE FROM daizx_User_Roles
WHERE role_id = OLD.id;
-- 记录删除信息
RAISE NOTICE '已清理角色 % 的所有权限和用户关联',
OLD.name;
RETURN OLD;
END;
$$;
alter function delete_role_permissions() owner to db_user30;
create trigger trg_delete_role_permissions before delete on daizx_roles for each row execute procedure delete_role_permissions();
create or replace function rollback_credit() returns trigger language plpgsql as $$ BEGIN -- 如果删除的成绩记录中学生通过了该课程（假设60分及以上为通过）
    IF OLD.dzx_score >= 60 THEN
UPDATE daizx_Students
SET dzx_student_total_credits = dzx_student_total_credits - (
        SELECT dzx_course_credits
        FROM daizx_Courses
        WHERE dzx_course_number = OLD.dzx_course_number
    )
WHERE dzx_student_number = OLD.dzx_student_number;
RAISE NOTICE '学生 % 的学分已回退，减少 % 学分',
OLD.dzx_student_number,
(
    SELECT dzx_course_credits
    FROM daizx_Courses
    WHERE dzx_course_number = OLD.dzx_course_number
);
END IF;
RETURN OLD;
END;
$$;
alter function rollback_credit() owner to db_user30;
create trigger trg_rollback_credit before delete on daizx_scores for each row execute procedure rollback_credit();
create or replace function auto_create_student_scores() returns trigger language plpgsql as $$ BEGIN -- 为新课程对应班级的所有学生创建成绩记录
INSERT INTO daizx_scores (
        dzx_student_number,
        dzx_course_number,
        dzx_academic_year,
        dzx_semester,
        dzx_score,
        dzx_teacher_number,
        dzx_grade_level,
        dzx_score_status
    )
SELECT s.dzx_student_number,
    NEW.dzx_course_number,
    NEW.dzx_academic_year,
    NEW.dzx_semester,
    NULL,
    -- 初始成绩为空
    NEW.dzx_teacher_number,
    NULL,
    -- 等级初始为空
    '正常' -- 默认状态为正常
FROM daizx_students s
WHERE s.dzx_class_id = NEW.dzx_class_id
    AND s.dzx_student_status = '在读' -- 只为在读学生创建成绩记录
    AND NOT EXISTS (
        -- 避免重复插入
        SELECT 1
        FROM daizx_scores sc
        WHERE sc.dzx_student_number = s.dzx_student_number
            AND sc.dzx_course_number = NEW.dzx_course_number
            AND sc.dzx_academic_year = NEW.dzx_academic_year
            AND sc.dzx_semester = NEW.dzx_semester
    );
-- 记录日志信息
RAISE NOTICE '为课程 % (%) 的班级学生自动创建了成绩记录',
NEW.dzx_course_name,
NEW.dzx_course_number;
RETURN NEW;
END;
$$;
alter function auto_create_student_scores() owner to db_user30;
create trigger trg_auto_create_student_scores
after
insert on daizx_courses for each row execute procedure auto_create_student_scores();