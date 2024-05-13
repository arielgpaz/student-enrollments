package com.apaz.studentenrollments.repositories;

import com.apaz.studentenrollments.domain.Enrollment;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByUserUsernameAndCourseCode(@NotNull String userId, @NotNull String courseId);

}
