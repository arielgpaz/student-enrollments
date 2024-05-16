package com.apaz.studentenrollments.controllers;

import com.apaz.studentenrollments.domain.Course;
import com.apaz.studentenrollments.domain.enums.StatusCourseEnum;
import com.apaz.studentenrollments.domain.request.CourseCreateRequest;
import com.apaz.studentenrollments.services.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody @Valid CourseCreateRequest request) {
        return ResponseEntity.ok(courseService.createCourse(request));
    }

    @DeleteMapping
    public ResponseEntity<Course> inactivateCourse(@RequestParam String code) {
        return ResponseEntity.ok(courseService.inactivateCourse(code));
    }

    @PutMapping
    public ResponseEntity<Course> reactivateCourse(@RequestParam String code) {
        return ResponseEntity.ok(courseService.reactivateCourse(code));
    }

    @GetMapping
    public ResponseEntity<Page<Course>> getCoursesByStatus(
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam StatusCourseEnum status) {
        return ResponseEntity.ok(courseService.getCoursesByStatus(pageable, status));
    }

}
