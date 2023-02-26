package com.todrepus.enrollmentsys.domain.member;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.course.CourseRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
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
                .userId("170108")
                .name("김아무개")
                .password("example@naver.com")
                .role(Role.STUDENT)
                .build();

        memberRepository.save(member);

        //when
        Optional<Member> optUser = memberRepository.findByUserId("170108");

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
                .password("123456")
                .role(Role.STUDENT)
                .build();

        Member member2 = Member.builder()
                .name("김교수")
                .password("123456")
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
        Assertions.assertThat(foundProfessor.getCourseSet()).contains(course);
        Assertions.assertThat(foundProfessor.getCourseSet()).contains(foundCourse);
    }

    @Test
    public void 회원_업데이트_테스트(){
        //given
        Member member = Member.builder()
                .userId("170108")
                .name("김아무개")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        memberRepository.save(member);

        //when
        member.update("새로운이름", member.getPassword(), member.getRole(), member.getCourseSet());

        //then
        Member user = memberRepository.findByUserId(member.getUserId()).orElseThrow(()-> new NoSuchElementException("회원을 찾지 못했습니다."));
        Assertions.assertThat(user.getName()).isEqualTo("새로운이름");
    }
}