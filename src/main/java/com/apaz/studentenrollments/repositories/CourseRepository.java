package com.apaz.studentenrollments.repositories;

import com.apaz.studentenrollments.domain.Course;
import com.apaz.studentenrollments.domain.enums.StatusCourseEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCode(String code);

    boolean existsByCodeAndStatus(String code, StatusCourseEnum status);

    Page<Course> findByStatus(Pageable pageable, StatusCourseEnum status);

    Optional<Course> findByCodeAndStatus(String code, StatusCourseEnum status);
}
