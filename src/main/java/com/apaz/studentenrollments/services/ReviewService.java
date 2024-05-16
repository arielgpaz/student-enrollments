package com.apaz.studentenrollments.services;

import com.apaz.studentenrollments.domain.Course;
import com.apaz.studentenrollments.domain.Review;
import com.apaz.studentenrollments.domain.enums.StatusCourseEnum;
import com.apaz.studentenrollments.domain.request.ReviewRequest;
import com.apaz.studentenrollments.domain.responses.CourseNpsResponse;
import com.apaz.studentenrollments.domain.responses.NpsReportResponse;
import com.apaz.studentenrollments.exceptions.ActiveCourseNotFoundException;
import com.apaz.studentenrollments.exceptions.UserNotFoundException;
import com.apaz.studentenrollments.repositories.CourseRepository;
import com.apaz.studentenrollments.repositories.ReviewRepository;
import com.apaz.studentenrollments.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ReviewRepository reviewRepository;
    private final EmailSender emailSender;

    private static final String SUBJECT = "You received a course review!";

    public Review reviewCourse(ReviewRequest request) {

        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UserNotFoundException(request.username()));

        var course = courseRepository.findByCodeAndStatus(request.courseCode(), StatusCourseEnum.ACTIVE)
                .orElseThrow(() -> new ActiveCourseNotFoundException(request.courseCode()));

        if (request.rating() <= 6) {
            sendEmail(request, course);
        }

        return reviewRepository.save(Review.builder()
                .user(user)
                .course(course)
                .rating(request.rating())
                .description(request.description())
                .build());
    }

    private void sendEmail(ReviewRequest request, Course course) {
        var body = """
                You received a review in the course %s!
                
                Rating: %s
                
                Description: %s
                
                """
                .formatted(course.getName(), request.rating(), request.description());

        emailSender.send(course.getInstructor().getEmail(), SUBJECT, body);
    }

    public NpsReportResponse npsReportGenerate() {

        List<Review> reviews = reviewRepository.findAllReviewsFromCoursesWithMoreThan4Reviews();

        Map<String, List<Integer>> reviewsByCourseId = groupReviewsByCourseCode(reviews);

        List<CourseNpsResponse> coursesNps = calculateNps(reviewsByCourseId);

        return NpsReportResponse.builder()
                .npsList(coursesNps)
                .build();
    }

    private Map<String, List<Integer>> groupReviewsByCourseCode(List<Review> reviews) {
        Map<String, List<Integer>> reviewsByCourseId = new HashMap<>();

        for (var review : reviews) {
            var courseId = review.getCourse().getCode();
            int rating = review.getRating();

            reviewsByCourseId.computeIfAbsent(courseId, k -> new ArrayList<>())
                    .add(rating);
        }

        return reviewsByCourseId;
    }

    private List<CourseNpsResponse> calculateNps(Map<String, List<Integer>> reviewsByCourseId) {

        List<CourseNpsResponse> coursesNps = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : reviewsByCourseId.entrySet()) {

            String courseCode = entry.getKey();
            List<Integer> ratings = entry.getValue();

            var nps = calculate(ratings);

            coursesNps.add(CourseNpsResponse.builder()
                    .courseCode(courseCode)
                    .nps(nps)
                    .build());
        }

        return coursesNps;
    }

    private Integer calculate(List<Integer> ratings) {

        float promotersCount = countRatings(ratings, 9, 10);
        float detractorsCount = countRatings(ratings, 0, 6);
        int totalResponses = ratings.size();

        float nps = ((promotersCount - detractorsCount) / totalResponses) * 100;

        return Math.round(nps);
    }

    private long countRatings(List<Integer> ratings, int minValue, int maxValue) {
        return ratings.stream()
                .filter(rating -> rating >= minValue && rating <= maxValue)
                .count();
    }

}
