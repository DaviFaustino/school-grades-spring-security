ALTER TABLE tb_report_cards
ADD CONSTRAINT fk_teacher_name FOREIGN KEY (teacher_username) REFERENCES tb_users(username),
ADD CONSTRAINT fk_student_name FOREIGN KEY (student_username) REFERENCES tb_users(username)
