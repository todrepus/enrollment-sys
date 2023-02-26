CREATE TABLE `enrollsys`.`course_enrolls` (
    student_id INT NOT NULL,
    course_id INT NOT NULL,
	date    TIMESTAMP      NOT NULL	DEFAULT CURRENT_TIMESTAMP,
    primary key(student_id, course_id)
);
