### Users ###
INSERT INTO se_db.`user`
(id, name, username, password, email, `role`, creation_date)
VALUES(1, 'Ariel', 'agdapaz', '123456', 'agdapaz@gmail.com', 'ESTUDANTE', '2024-05-14 20:05:36');
INSERT INTO se_db.`user`
(id, name, username, password, email, `role`, creation_date)
VALUES(2, 'Gabi', 'gmazon', '123456', 'gabid@gmail.com', 'ADMIN', '2024-05-14 20:23:32');
INSERT INTO se_db.`user`
(id, name, username, password, email, `role`, creation_date)
VALUES(3, 'Otavio', 'omupa', '123456', 'omupa@gmail.com', 'INSTRUTOR', '2024-05-14 20:24:27');

### Courses ###
INSERT INTO se_db.course
(id, name, code, instructor_id, description, status, creation_date, inactivation_date)
VALUES(1, 'Java EE', 'curso-java', 1, 'Aprenda em 6 meses', 'ACTIVE', '2024-05-14 20:05:43', NULL);
INSERT INTO se_db.course
(id, name, code, instructor_id, description, status, creation_date, inactivation_date)
VALUES(2, 'Spring', 'curso-spring', 2, 'Sua primeira API', 'ACTIVE', '2024-05-14 20:24:06', NULL);

### Enrollments ###
INSERT INTO se_db.enrollment
(id, user_id, course_id, `date`)
VALUES(1, 1, 1, '2024-05-14 20:06:17');

### Reviews ###
INSERT INTO se_db.review
(id, course_id, user_id, rating, description, creation_date)
VALUES(1, 1, 1, 3, 'Melhor curso', '2024-05-14 20:06:27');
INSERT INTO se_db.review
(id, course_id, user_id, rating, description, creation_date)
VALUES(2, 1, 1, 3, 'Melhor curso', '2024-05-14 20:27:37');
INSERT INTO se_db.review
(id, course_id, user_id, rating, description, creation_date)
VALUES(3, 1, 1, 5, 'Melhor curso', '2024-05-14 20:27:43');
INSERT INTO se_db.review
(id, course_id, user_id, rating, description, creation_date)
VALUES(4, 1, 1, 7, 'Melhor curso', '2024-05-14 20:27:47');
INSERT INTO se_db.review
(id, course_id, user_id, rating, description, creation_date)
VALUES(5, 1, 1, 9, 'Melhor curso', '2024-05-14 20:27:50');
INSERT INTO se_db.review
(id, course_id, user_id, rating, description, creation_date)
VALUES(6, 1, 1, 9, 'Melhor curso', '2024-05-14 20:27:51');
INSERT INTO se_db.review
(id, course_id, user_id, rating, description, creation_date)
VALUES(7, 1, 1, 9, 'Melhor curso', '2024-05-14 20:27:52');
INSERT INTO se_db.review
(id, course_id, user_id, rating, description, creation_date)
VALUES(8, 1, 1, 9, 'Melhor curso', '2024-05-14 20:27:54');
