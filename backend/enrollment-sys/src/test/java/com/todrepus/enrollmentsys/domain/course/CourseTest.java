package com.todrepus.enrollmentsys.domain.course;

import com.todrepus.enrollmentsys.domain.member.Member;
import com.todrepus.enrollmentsys.domain.member.MemberRepository;
import com.todrepus.enrollmentsys.domain.member.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseTest {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void setup(){
        Member member = Member.builder()
                .name("김교수")
                .email("example@email.com")
                .role(Role.PROFESSOR)
                .build();
        memberRepository.save(member);
    }
    @Test
    public void CourseDateList_변환테스트(){
        //given
        Member professor = memberRepository.findAll().get(0);
        List<String> courseDateStrs = List.of(
                "MON 14:40~16:00", "WED 17:00~19:30"
        );
        List<CourseDate> courseDateList = courseDateStrs.stream().map(CourseDate::Of).toList();
        Course course = Course.builder()
                .courseDateList(courseDateList)
                .room("충무관")
                .department("전자정보통신공학과")
                .maxNum(50)
                .enrollNum(0)
                .professor(professor).build();

        //when
        courseRepository.save(course);
        Course foundCourse = courseRepository.findAll().get(0);
        Assertions.assertThat(foundCourse.getCourseDateList().toString()).isEqualTo("[MON 14:40~16:0, WED 17:0~19:30]");
    }

}