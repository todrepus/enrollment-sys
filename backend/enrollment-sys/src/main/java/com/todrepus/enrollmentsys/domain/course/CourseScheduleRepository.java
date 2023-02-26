package com.todrepus.enrollmentsys.domain.course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {
    void deleteByCourseId(Long courseId);
}
