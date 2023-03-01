package com.todrepus.enrollmentsys.domain.course;

import com.todrepus.enrollmentsys.domain.courseEnroll.CourseEnrollService;
import com.todrepus.enrollmentsys.domain.member.MemberService;
import com.todrepus.enrollmentsys.domain.member.Professor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final MemberService memberService;
    private final CourseEnrollService courseEnrollService;
    private final CourseScheduleService courseScheduleService;

    public List<Course> findCourseListStartWith(String words, int recommend_num){
        return courseRepository.findAll().stream()
                .filter((course -> course.getName().startsWith(words)))
                .limit(recommend_num)
                .toList();
    }

    public Course findCourse(Long courseId){
        return courseRepository.findById(courseId).orElseThrow(
                () -> new NoSuchElementException("해당 id를 가진 course가 존재하지 않습니다")
        );
    }

    public Course deleteCourse(Course course){
        // 강의 - 교수 연관관계 삭제
        Professor professor = course.getProfessor();
        if (professor != null)
            memberService.cancelCourseAssign(professor, course);
        course.setProfessor(null);

        // 강의 - 학생, 강의 - 시간 연관관계 삭제
        courseEnrollService.deleteCourseEnrollsByCourse(course);
        courseScheduleService.deleteCourseSchedulesByCourse(course);
        course.getCourseScheduleList().clear();

        courseRepository.delete(course);

        return course;
    }
}
