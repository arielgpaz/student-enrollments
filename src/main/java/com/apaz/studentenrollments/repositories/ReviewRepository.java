package com.apaz.studentenrollments.repositories;

import com.apaz.studentenrollments.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
            SELECT r
            FROM Review  r
            INNER JOIN (
              SELECT c.id AS course_id
              FROM Course c
              INNER JOIN Review rev ON c.id = rev.course.id
              GROUP BY c.id
              HAVING COUNT(*) > 4
            ) AS course_with_more_than_4_reviews
            ON r.course.id = course_with_more_than_4_reviews.course_id
            """)
    List<Review> findAllReviewsFromCoursesWithMoreThan4Reviews();



}
