package com.apaz.studentenrollments.controllers;

import com.apaz.studentenrollments.domain.Course;
import com.apaz.studentenrollments.domain.enums.StatusCourseEnum;
import com.apaz.studentenrollments.domain.request.CourseCreateRequest;
import com.apaz.studentenrollments.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public Course createCourse(@RequestBody CourseCreateRequest request) {
        return courseService.createCourse(request);
    }

    @DeleteMapping
    public Course inactivateCourse(@RequestParam String code) {
        return courseService.inactivateCourse(code);
    }

    @PutMapping
    public Course reactivateCourse(@RequestParam String code) {
        return courseService.reactivateCourse(code);
    }

    @GetMapping
    public Page<Course> getCoursesByStatus(
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam StatusCourseEnum status) {
        return courseService.getCoursesByStatus(pageable, status);
    }
}
