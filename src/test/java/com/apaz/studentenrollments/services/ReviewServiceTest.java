package com.apaz.studentenrollments.services;

import com.apaz.studentenrollments.domain.Course;
import com.apaz.studentenrollments.domain.Review;
import com.apaz.studentenrollments.domain.User;
import com.apaz.studentenrollments.domain.enums.RoleType;
import com.apaz.studentenrollments.domain.enums.StatusCourseEnum;
import com.apaz.studentenrollments.domain.request.ReviewRequest;
import com.apaz.studentenrollments.domain.responses.NpsReportResponse;
import com.apaz.studentenrollments.exceptions.ActiveCourseNotFoundException;
import com.apaz.studentenrollments.exceptions.UserNotFoundException;
import com.apaz.studentenrollments.repositories.CourseRepository;
import com.apaz.studentenrollments.repositories.ReviewRepository;
import com.apaz.studentenrollments.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void testReviewCourse_Success_LowRating() {
        ReviewRequest request = new ReviewRequest("user1", "course-code", 4, "This course could be improved.");
        User user = User.builder()
                .name("User Name")
                .username("username")
                .email("email")
                .password("123")
                .role(RoleType.ADMIN)
                .build();
        Course course = Course.builder()
                .instructor(user)
                .build();
        Review expectedReview = Review.builder()
                .user(user)
                .course(course)
                .rating(request.rating())
                .description(request.description())
                .build();

        when(userRepository.findByUsername(request.username())).thenReturn(Optional.of(user));
        when(courseRepository.findByCodeAndStatus(request.courseCode(), StatusCourseEnum.ACTIVE)).thenReturn(Optional.of(course));
        when(reviewRepository.save(any(Review.class))).thenReturn(expectedReview);

        Review actualReview = reviewService.reviewCourse(request);

        assertEquals(expectedReview, actualReview);
        verify(reviewRepository).save(any(Review.class));
        verify(emailSender, times(1)).send(anyString(), anyString(), anyString());
    }

    @Test
    public void testReviewCourse_Success_HighRating() {
        ReviewRequest request = new ReviewRequest("user1", "course-code", 8, "Great course!");
        User user = User.builder()
                .name("User Name")
                .username("username")
                .email("email")
                .password("123")
                .role(RoleType.ADMIN)
                .build();
        Course course = new Course();
        Review expectedReview = Review.builder()
                .user(user)
                .course(course)
                .rating(request.rating())
                .description(request.description())
                .build();

        when(userRepository.findByUsername(request.username())).thenReturn(Optional.of(user));
        when(courseRepository.findByCodeAndStatus(request.courseCode(), StatusCourseEnum.ACTIVE)).thenReturn(Optional.of(course));
        when(reviewRepository.save(any(Review.class))).thenReturn(expectedReview);

        Review actualReview = reviewService.reviewCourse(request);

        assertEquals(expectedReview, actualReview);
        verify(reviewRepository).save(any(Review.class));
        verify(emailSender, times(0)).send(anyString(), anyString(), anyString());
    }

    @Test
    public void testReviewCourse_UserNotFound() {
        ReviewRequest request = new ReviewRequest("user1", "course-code", 4, "This course could be improved.");

        when(userRepository.findByUsername(request.username())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> reviewService.reviewCourse(request));
    }

    @Test
    public void testReviewCourse_ActiveCourseNotFound() {
        ReviewRequest request = new ReviewRequest("user1", "course-code", 4, "This course could be improved.");
        User user = new User();

        when(userRepository.findByUsername(request.username())).thenReturn(Optional.of(user));
        when(courseRepository.findByCodeAndStatus(request.courseCode(), StatusCourseEnum.ACTIVE)).thenReturn(Optional.empty());

        assertThrows(ActiveCourseNotFoundException.class, () -> reviewService.reviewCourse(request));
    }

    @Test
    public void testNpsReportGenerate_EmptyReviews() {
        Mockito.when(reviewRepository.findAllReviewsFromCoursesWithMoreThan4Reviews()).thenReturn(Collections.emptyList());

        NpsReportResponse response = reviewService.npsReportGenerate();

        assertEquals(Collections.emptyList(), response.getNpsList());
        Mockito.verify(reviewRepository).findAllReviewsFromCoursesWithMoreThan4Reviews();
        Mockito.verify(reviewRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void testNpsReportGenerate_MultipleCourses() {
        List<Review> reviews = createReviews();

        Mockito.when(reviewRepository.findAllReviewsFromCoursesWithMoreThan4Reviews()).thenReturn(reviews);

        NpsReportResponse response = reviewService.npsReportGenerate();

        assertEquals(2, response.getNpsList().size());
        assertEquals(0, response.getNpsList().get(0).getNps());
        assertEquals(100, response.getNpsList().get(1).getNps());
        Mockito.verify(reviewRepository).findAllReviewsFromCoursesWithMoreThan4Reviews();
    }

    private List<Review> createReviews() {
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            String courseCode = "course" + (i + 1);
            Course course = Course.builder()
                    .code(courseCode)
                    .build();
            reviews.add(Review.builder()
                    .course(course)
                    .rating(9 - i)
                    .build());
        }
        return reviews;
    }
}
