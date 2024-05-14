package com.apaz.studentenrollments.services;

import com.apaz.studentenrollments.domain.Course;
import com.apaz.studentenrollments.domain.User;
import com.apaz.studentenrollments.domain.enums.StatusCourseEnum;
import com.apaz.studentenrollments.domain.request.CourseCreateRequest;
import com.apaz.studentenrollments.exceptions.CodeAlreadyInUseException;
import com.apaz.studentenrollments.exceptions.CourseNotFoundException;
import com.apaz.studentenrollments.exceptions.UserNotFoundException;
import com.apaz.studentenrollments.repositories.CourseRepository;
import com.apaz.studentenrollments.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public Course createCourse(CourseCreateRequest request) {

        if (courseRepository.existsByCodeAndStatus(request.code(), StatusCourseEnum.ACTIVE)) {
            throw new CodeAlreadyInUseException(request.code());
        }

        User instructor = userRepository.findByUsername(request.instructorUsername())
                .orElseThrow( () -> new UserNotFoundException(request.instructorUsername()));

        return courseRepository.save(Course.builder()
                .name(request.name())
                .code(request.code())
                .instructor(instructor)
                .description(request.description())
                .status(StatusCourseEnum.ACTIVE)
                .build());
    }

    public Course inactivateCourse(String code) {

        Course course = courseRepository.findByCode(code)
                .orElseThrow(() -> new CourseNotFoundException(code));

        course.setStatus(StatusCourseEnum.INACTIVE);
        course.setInactivationDate(LocalDateTime.now());

        return courseRepository.save(course);
    }

    public Course reactivateCourse(String code) {

        Course course = courseRepository.findByCode(code)
                .orElseThrow(() -> new CourseNotFoundException(code));

        course.setStatus(StatusCourseEnum.ACTIVE);
        course.setInactivationDate(null);

        return courseRepository.save(course);
    }

    public Page<Course> getCoursesByStatus(Pageable pageable, StatusCourseEnum status) {
        return courseRepository.findByStatus(pageable, status);
    }
}
