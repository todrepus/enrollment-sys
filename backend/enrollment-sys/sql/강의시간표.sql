CREATE TABLE `enrollsys`.`course_schedules` (
	id INT NOT NULL PRIMARY KEY,
    course_id INT NOT NULL,
    course_day enum('SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'),
    course_hour INT NOT NULL,
    course_min INT NOT NULL
);
