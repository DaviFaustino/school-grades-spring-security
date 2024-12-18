CREATE TABLE tb_report_cards (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    teacher_username VARCHAR(30) NOT NULL,
    school_subject VARCHAR(20) NOT NULL,
    student_username VARCHAR(30) NOT NULL,
    school_year VARCHAR(4) NOT NULL,
    grades VARCHAR(5) ARRAY[4],
    UNIQUE(teacher_username, school_subject, student_username, school_year)
)
