package com.apaz.studentenrollments.controllers;

import com.apaz.studentenrollments.domain.Review;
import com.apaz.studentenrollments.domain.request.ReviewRequest;
import com.apaz.studentenrollments.services.CourseReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("review")
@RequiredArgsConstructor
public class CourseReviewController {

    private final CourseReviewService courseReviewService;

    @PostMapping()
    public Review reviewCourse(@RequestBody ReviewRequest request) {
        return courseReviewService.reviewCourse(request);
    }
}
