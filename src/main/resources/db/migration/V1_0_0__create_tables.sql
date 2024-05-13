CREATE TABLE IF NOT EXISTS user
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    name          VARCHAR(255)                             NOT NULL,
    username      VARCHAR(255)                             NOT NULL,
    password      VARCHAR(255)                             NOT NULL,
    email         VARCHAR(255)                             NOT NULL,
    role          ENUM ('ADMIN', 'ESTUDANTE', 'INSTRUTOR') NOT NULL,
    creation_date DATETIME                                 NOT NULL
);

CREATE TABLE IF NOT EXISTS `course`
(
    `id`                BIGINT                      NOT NULL AUTO_INCREMENT,
    `name`              VARCHAR(255)                NOT NULL,
    `code`              VARCHAR(255)                NOT NULL UNIQUE,
    `instructor`        VARCHAR(255)                NOT NULL,
    `description`       TEXT,
    `status`            ENUM ('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE',
    `creation_date`     DATETIME                    NOT NULL,
    `inactivation_date` DATETIME,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `enrollment`
(
    id        BIGINT   NOT NULL AUTO_INCREMENT,
    user_id   BIGINT   NOT NULL,
    course_id BIGINT   NOT NULL,
    date      DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (course_id) REFERENCES course (id)
);
