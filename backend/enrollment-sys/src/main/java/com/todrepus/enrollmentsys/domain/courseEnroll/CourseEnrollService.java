package com.todrepus.enrollmentsys.domain.courseEnroll;

import com.todrepus.enrollmentsys.domain.course.Course;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseEnrollService {
    private final CourseEnrollRepository courseEnrollRepository;

    public List<CourseEnroll> deleteCourseEnrollsByCourse(Course course){
        List<CourseEnroll> courseEnrollList = courseEnrollRepository.findByCourseId(course.getId());
        courseEnrollList.forEach(courseEnrollRepository::delete);

        return courseEnrollList;
    }
}
