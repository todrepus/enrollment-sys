CREATE TABLE `enrollsys`.`courses` (
    course_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(25),
    professor_id INT NOT NULL, # 교수 FK
    room_id INT NOT NULL, # 강의실 FK
    department_id INT NOT NULL # 강의학과 FK
);
