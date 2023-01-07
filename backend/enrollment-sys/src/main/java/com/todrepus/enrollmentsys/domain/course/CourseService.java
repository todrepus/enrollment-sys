package com.todrepus.enrollmentsys.domain.course;

import com.todrepus.enrollmentsys.web.admin.AdminPageConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final int RECOMMEND_NUM = 10;

    public List<Course> findCoursesStartWith(String words){
        return courseRepository.findAll().stream()
                .filter((course -> course.getName().startsWith(words)))
                .limit(RECOMMEND_NUM)
                .toList();
    }
}
