package com.todrepus.enrollmentsys.domain.courseEnroll;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseEnrollRepository extends JpaRepository<CourseEnroll, Long> {
    Optional<CourseEnroll> findByStudentIdAndCourseId(Long studentId, Long courseId);
    List<CourseEnroll> findByCourseId(Long courseId);
}
