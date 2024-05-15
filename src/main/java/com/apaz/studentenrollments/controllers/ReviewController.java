package com.apaz.studentenrollments.controllers;

import com.apaz.studentenrollments.domain.Review;
import com.apaz.studentenrollments.domain.request.ReviewRequest;
import com.apaz.studentenrollments.domain.responses.NpsReportResponse;
import com.apaz.studentenrollments.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping()
    public Review reviewCourse(@RequestBody ReviewRequest request) {
        return reviewService.reviewCourse(request);
    }

    @GetMapping("report")
    public NpsReportResponse npsReportGenerate() {
        return reviewService.npsReportGenerate();
    }
}
