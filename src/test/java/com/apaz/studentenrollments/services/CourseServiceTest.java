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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void testCreateCourse_Success() {
        CourseCreateRequest request = new CourseCreateRequest("course-code", "Course Name", "instructor", "Description");
        User instructor = new User();
        Course expectedCourse = Course.builder()
                .name(request.name())
                .code(request.code())
                .instructor(instructor)
                .description(request.description())
                .status(StatusCourseEnum.ACTIVE)
                .build();

        when(courseRepository.existsByCodeAndStatus(request.code(), StatusCourseEnum.ACTIVE)).thenReturn(false);
        when(userRepository.findByUsername(request.instructorUsername())).thenReturn(Optional.of(instructor));
        when(courseRepository.save(any(Course.class))).thenReturn(expectedCourse);

        Course actualCourse = courseService.createCourse(request);

        assertEquals(expectedCourse, actualCourse);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    public void testCreateCourse_CodeAlreadyExists() {
        CourseCreateRequest request = new CourseCreateRequest("course-code", "Course Name", "instructor", "Description");

        when(courseRepository.existsByCodeAndStatus(request.code(), StatusCourseEnum.ACTIVE)).thenReturn(true);

        assertThrows(CodeAlreadyInUseException.class, () -> courseService.createCourse(request));
    }

    @Test
    public void testCreateCourse_UserNotFound() {
        CourseCreateRequest request = new CourseCreateRequest("course-code", "Course Name", "instructor", "Description");

        when(courseRepository.existsByCodeAndStatus(request.code(), StatusCourseEnum.ACTIVE)).thenReturn(false);
        when(userRepository.findByUsername(request.instructorUsername())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> courseService.createCourse(request));
    }

    @Test
    public void testInactivateCourse_Success() {
        String courseCode = "valid-code";
        Course existingCourse = Course.builder()
                .code(courseCode)
                .name("Course Name")
                .status(StatusCourseEnum.ACTIVE)
                .build();
        Course expectedCourse = Course.builder()
                .code(courseCode)
                .name("Course Name")
                .status(StatusCourseEnum.INACTIVE)
                .inactivationDate(LocalDateTime.now())
                .build();

        when(courseRepository.findByCode(courseCode)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(expectedCourse);

        Course inactivatedCourse = courseService.inactivateCourse(courseCode);

        assertEquals(expectedCourse, inactivatedCourse);
        assertEquals(StatusCourseEnum.INACTIVE, inactivatedCourse.getStatus());
        assertNotNull(inactivatedCourse.getInactivationDate());
        verify(courseRepository, times(1)).save(any(Course.class));
    }


    @Test
    public void testInactivateCourse_NotFound() {
        String courseCode = "invalid-code";

        when(courseRepository.findByCode(courseCode)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.inactivateCourse(courseCode));
    }

    @Test
    public void testReactivateCourse_Success() {
        String courseCode = "valid-code";
        Course existingCourse = Course.builder()
                .code(courseCode)
                .name("Course Name")
                .status(StatusCourseEnum.INACTIVE)
                .build();
        Course expectedCourse = Course.builder()
                .code(courseCode)
                .name("Course Name")
                .status(StatusCourseEnum.ACTIVE)
                .inactivationDate(LocalDateTime.now())
                .build();

        when(courseRepository.findByCode(courseCode)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(expectedCourse);

        Course inactivatedCourse = courseService.reactivateCourse(courseCode);

        assertEquals(expectedCourse, inactivatedCourse);
        assertEquals(StatusCourseEnum.ACTIVE, inactivatedCourse.getStatus());
        assertNotNull(inactivatedCourse.getInactivationDate());
        verify(courseRepository, times(1)).save(any(Course.class));
    }


    @Test
    public void testReactivateCourse_NotFound() {
        String courseCode = "invalid-code";

        when(courseRepository.findByCode(courseCode)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.reactivateCourse(courseCode));
    }

    @Test
    public void testGetCoursesByStatus_Success() {
        StatusCourseEnum expectedStatus = StatusCourseEnum.ACTIVE;
        Course existingCourse = Course.builder()
                .code("course-code")
                .name("Course Name")
                .status(StatusCourseEnum.ACTIVE)
                .build();
        List<Course> courses = Collections.singletonList(existingCourse);
        Page<Course> expectedPage = new PageImpl<>(courses, pageable, courses.size());

        when(courseRepository.findByStatus(pageable, expectedStatus)).thenReturn(expectedPage);

        Page<Course> actualPage = courseService.getCoursesByStatus(pageable, expectedStatus);

        assertEquals(expectedPage, actualPage);
        assertEquals(expectedStatus, actualPage.getContent().getFirst().getStatus());
        verify(courseRepository, times(1)).findByStatus(pageable, expectedStatus);
    }
}
