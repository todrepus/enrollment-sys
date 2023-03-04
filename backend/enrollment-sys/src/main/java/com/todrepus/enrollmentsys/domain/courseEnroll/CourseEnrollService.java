package com.todrepus.enrollmentsys.domain.courseEnroll;

import com.todrepus.enrollmentsys.domain.course.Course;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public CourseEnroll findCourseEnrollById(Long enrollId){
        return courseEnrollRepository.findById(enrollId).orElseThrow(
                () -> new NoSuchElementException("해당 id를 가진 enroll이 존재하지 않습니다.")
        );
    }
}
