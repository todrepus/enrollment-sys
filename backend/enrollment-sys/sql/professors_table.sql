CREATE TABLE `enrollsys`.`professors` (
    user_id INT NOT NULL PRIMARY KEY,
    department_id INT NOT NULL,  # 학과 FK
    year INT NULL  # 연차
);
