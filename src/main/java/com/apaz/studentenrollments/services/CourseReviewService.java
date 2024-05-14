package com.apaz.studentenrollments.services;

import com.apaz.studentenrollments.domain.Course;
import com.apaz.studentenrollments.domain.Review;
import com.apaz.studentenrollments.domain.User;
import com.apaz.studentenrollments.domain.enums.StatusCourseEnum;
import com.apaz.studentenrollments.domain.request.ReviewRequest;
import com.apaz.studentenrollments.exceptions.ActiveCourseNotFoundException;
import com.apaz.studentenrollments.exceptions.UserNotFoundException;
import com.apaz.studentenrollments.repositories.CourseRepository;
import com.apaz.studentenrollments.repositories.CourseReviewRepository;
import com.apaz.studentenrollments.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseReviewService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseReviewRepository courseReviewRepository;
    private final EmailSender emailSender;

    public Review reviewCourse(ReviewRequest request) {

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UserNotFoundException(request.username()));

        Course course = courseRepository.findByCodeAndStatus(request.courseCode(), StatusCourseEnum.ACTIVE)
                .orElseThrow(() -> new ActiveCourseNotFoundException(request.courseCode()));

        if (request.rating() <= 6) {
            String body = """
                     You received a review in the course %s!
                     Rating: %s
                     Description: %s
                     """.formatted(course.getName(), request.rating(), request.description());

            emailSender.send(course.getInstructor(), "You received a course review!", body);
        }

        return courseReviewRepository.save(Review.builder().user(user).course(course).rating(request.rating()).description(request.description()).build());
    }
}
