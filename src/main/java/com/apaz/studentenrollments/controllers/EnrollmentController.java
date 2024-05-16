package com.apaz.studentenrollments.controllers;

import com.apaz.studentenrollments.domain.Enrollment;
import com.apaz.studentenrollments.domain.request.EnrollmentRequest;
import com.apaz.studentenrollments.services.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<Enrollment> enroll(@RequestBody EnrollmentRequest request) {
        return ResponseEntity.ok(enrollmentService.enroll(request));
    }

}
