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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Test
    public void testEnroll_Success() {
        EnrollmentRequest request = new EnrollmentRequest("user1", "course-code");
        User user = new User();
        Course course = new Course();
        Enrollment expectedEnrollment = new Enrollment();

        when(userRepository.findByUsername(request.username())).thenReturn(Optional.of(user));
        when(courseRepository.findByCodeAndStatus(request.courseCode(), StatusCourseEnum.ACTIVE)).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByUserUsernameAndCourseCode(request.username(), request.courseCode())).thenReturn(false);
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(expectedEnrollment);

        Enrollment actualEnrollment = enrollmentService.enroll(request);

        assertEquals(expectedEnrollment, actualEnrollment);
        verify(enrollmentRepository).save(any(Enrollment.class));
    }

    @Test
    public void testEnroll_UserNotFound() {
        EnrollmentRequest request = new EnrollmentRequest("user1", "course-code");

        when(userRepository.findByUsername(request.username())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> enrollmentService.enroll(request));
    }

    @Test
    public void testEnroll_ActiveCourseNotFound() {
        EnrollmentRequest request = new EnrollmentRequest("user1", "course-code");
        User user = new User();

        when(userRepository.findByUsername(request.username())).thenReturn(Optional.of(user));
        when(courseRepository.findByCodeAndStatus(request.courseCode(), StatusCourseEnum.ACTIVE)).thenReturn(Optional.empty());

        assertThrows(ActiveCourseNotFoundException.class, () -> enrollmentService.enroll(request));
    }

    @Test
    public void testEnroll_StudentAlreadyEnrolled() {
        EnrollmentRequest request = new EnrollmentRequest("user1", "course-code");

        when(enrollmentRepository.existsByUserUsernameAndCourseCode(request.username(), request.courseCode())).thenReturn(true);

        assertThrows(EnrolledStudentException.class, () -> enrollmentService.enroll(request));
    }
}

