package com.apaz.studentenrollments.services;

import com.apaz.studentenrollments.domain.Course;
import com.apaz.studentenrollments.domain.Enrollment;
import com.apaz.studentenrollments.domain.User;
import com.apaz.studentenrollments.domain.enums.StatusCourseEnum;
import com.apaz.studentenrollments.domain.request.EnrollmentRequest;
import com.apaz.studentenrollments.exceptions.ActiveCourseNotFoundException;
import com.apaz.studentenrollments.exceptions.EnrolledStudentException;
import com.apaz.studentenrollments.exceptions.UserNotFoundException;
import com.apaz.studentenrollments.repositories.CourseRepository;
import com.apaz.studentenrollments.repositories.EnrollmentRepository;
import com.apaz.studentenrollments.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public Enrollment enroll(EnrollmentRequest request) {

        this.validateRequest(request);

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UserNotFoundException(request.username()));

        Course course = courseRepository.findByCodeAndStatus(request.courseCode(), StatusCourseEnum.ACTIVE)
                .orElseThrow(() -> new ActiveCourseNotFoundException(request.courseCode()));

        return enrollmentRepository.save(Enrollment.builder()
                .user(user)
                .course(course)
                .build());
    }

    private void validateRequest(EnrollmentRequest request) {

        boolean isTheStudentEnrolled = enrollmentRepository.existsByUserUsernameAndCourseCode(
                request.username(),
                request.courseCode());

        if (isTheStudentEnrolled) {
            throw new EnrolledStudentException(request.username(), request.courseCode());
        }

    }
}
