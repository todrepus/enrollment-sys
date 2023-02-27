package com.todrepus.enrollmentsys.domain.course;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {
    void deleteByCourseId(Long courseId);
    List<CourseSchedule> findByCourseId(Long courseId);
}
