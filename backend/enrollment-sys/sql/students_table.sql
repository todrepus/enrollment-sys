CREATE TABLE `enrollsys`.`students` (
    user_id INT NOT NULL PRIMARY KEY,
    semester INT NOT NULL,
    state enum('ENROLLED', 'LEAVE', 'GRADUATED') # (재학, 휴학, 졸업)
);

