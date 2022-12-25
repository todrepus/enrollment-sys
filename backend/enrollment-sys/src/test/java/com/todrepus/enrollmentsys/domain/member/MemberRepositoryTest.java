package com.todrepus.enrollmentsys.domain.member;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.course.CourseDate;
import com.todrepus.enrollmentsys.domain.course.CourseRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CourseRepository courseRepository;

    @AfterEach
    public void after(){
        memberRepository.deleteAll();
    }
    @Test
    public void Member_저장() throws Exception {
        //given
        Member member = Member.builder()
                .name("김아무개")
                .email("example@naver.com")
                .role(Role.STUDENT)
                .build();

        memberRepository.save(member);

        //when
        Optional<Member> optUser = memberRepository.findByEmail("example@naver.com");

        //then
        Assertions.assertThat(memberRepository.findAll().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(optUser.isPresent()).isEqualTo(true);
        Member foundMember = optUser.get();
        org.assertj.core.api.Assertions.assertThat(foundMember.getName()).isEqualTo("김아무개");
    }

    @Test
    public void CourseList_살펴보기_and_강의중복등록_방지확인() {
        Member member = Member.builder()
                .name("김학생")
                .email("example@naver.com")
                .role(Role.STUDENT)
                .build();

        Member member2 = Member.builder()
                .name("김교수")
                .email("professor@naver.com")
                .role(Role.PROFESSOR)
                .build();

        memberRepository.save(member);
        memberRepository.save(member2);

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
                .professor(member2).build();
        courseRepository.save(course);
        member2.addCourse(course);

        //같은 id의 강의가 중복등록되는지 확인해보기
        Course foundCourse = courseRepository.findById(course.getId()).get();
        Assertions.assertThat(member2.addCourse(foundCourse)).isEqualTo(false);

        //courseList가 courseId에 잘 매핑되는지 확인
        //course와 foundCourse가 같은 값으로 표현되는지 확인
        Member foundProfessor = memberRepository.findById(member2.getId()).get();
        Assertions.assertThat(foundProfessor.getCourseList()).contains(course);
        Assertions.assertThat(foundProfessor.getCourseList()).contains(foundCourse);
    }

}