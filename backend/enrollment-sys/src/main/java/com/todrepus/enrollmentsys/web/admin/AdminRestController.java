package com.todrepus.enrollmentsys.web.admin;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.course.CourseRepository;
import com.todrepus.enrollmentsys.domain.course.CourseService;
import com.todrepus.enrollmentsys.domain.member.Member;
import com.todrepus.enrollmentsys.domain.member.MemberRepository;
import com.todrepus.enrollmentsys.domain.member.MemberService;
import com.todrepus.enrollmentsys.domain.member.Role;
import com.todrepus.enrollmentsys.web.RestResponseDTO;
import com.todrepus.enrollmentsys.web.RestState;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api/admin")
@RestController
public class AdminRestController {
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final MemberService memberService;
    private final CourseService courseService;


    /// 회원검색어 추천
    @GetMapping("/recommend/courses")
    public RestResponseDTO recommendCourseList(@RequestParam String content){
        List<Course> courseList =  courseService.findCoursesStartWith(content);
        RestResponseDTO responseDTO = RestResponseDTO.builder()
                .state(RestState.OK)
                .message("강의추천")
                .build();
        responseDTO.addParam("courseList", courseList);
        return responseDTO;
    }


    /*
    강의 수정 및 관리
     */

}
