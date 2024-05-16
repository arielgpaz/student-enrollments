package com.apaz.studentenrollments.controllers;

import com.apaz.studentenrollments.domain.Review;
import com.apaz.studentenrollments.domain.request.ReviewRequest;
import com.apaz.studentenrollments.domain.responses.NpsReportResponse;
import com.apaz.studentenrollments.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping()
    public Review reviewCourse(@RequestBody ReviewRequest request) {
        return reviewService.reviewCourse(request);
    }

    @GetMapping("report")
    public ResponseEntity<NpsReportResponse> npsReportGenerate() {
        return ResponseEntity.ok(reviewService.npsReportGenerate());
    }

}
