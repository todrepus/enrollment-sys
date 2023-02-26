//// -- LEVEL 1
//// -- Schemas, Tables and References

// Creating tables
// You can define the tables with full schema names
Table courses{
  course_id int pk
  name varchar(25)
  professor_id int
  room_id int
  department_id int
  enroll_num int
  max_num int
}
Table departments{
  department_id int pk
  name varchar(18)
}

Table users{
  id int pk
  user_id varchar(12)
  name varchar(25)
  phone_number char(11)
  role ENUM('STUDENT','PROFESSOR','ADMIN')
  password varchar(26)
}

Table professors{
  professor_id int pk [ref: - users.id]
  department_id int
  year int
}
Table students{
  student_id int pk [ref: - users.id]
  semester int
  state ENUM('ENROLLED', 'GRADUATED', 'LEAVE')
  department_id int
}

Table rooms{
  room_id int pk
  location varchar(8)
  ho varchar(8)
}

Table course_schedules{
  id int pk
  course_id int
  course_day ENUM
  course_hour_start int
  course_min_start int
  course_hour_end int
  course_min_end int
}

Table course_enrolls{
  id int pk
  student_id int
  course_id int
  created_date timestamp
}

ref : professors.department_id > departments.department_id
ref : students.student_id > departments.department_id

ref : courses.department_id > departments.department_id
ref : courses.professor_id > professors.professor_id
ref : courses.room_id > rooms.room_id

ref : course_schedules.course_id > courses.course_id

ref : course_enrolls.student_id > students.student_id
ref : course_enrolls.course_id > courses.course_id

