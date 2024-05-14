CREATE TABLE IF NOT EXISTS review
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id     BIGINT       NOT NULL,
    user_id       BIGINT       NOT NULL,
    rating        INT CHECK (rating >= 1 AND rating <= 10),
    description   VARCHAR(255) NOT NULL,
    creation_date DATETIME     NOT NULL,
    FOREIGN KEY (course_id) REFERENCES course (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);
