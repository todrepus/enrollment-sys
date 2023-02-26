package com.todrepus.enrollmentsys.domain.course;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseScheduleService {
    private final CourseScheduleRepository courseScheduleRepository;

    public void deleteCourseSchedulesByCourse(Course course){
        courseScheduleRepository.deleteByCourseId(course.getId());
    }
}
