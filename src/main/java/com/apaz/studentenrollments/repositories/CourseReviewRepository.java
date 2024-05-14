package com.apaz.studentenrollments.repositories;

import com.apaz.studentenrollments.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseReviewRepository extends JpaRepository<Review, Long> {
}
