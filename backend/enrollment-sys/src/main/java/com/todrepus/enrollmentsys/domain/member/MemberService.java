package com.todrepus.enrollmentsys.domain.member;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.courseEnroll.CourseEnroll;
import com.todrepus.enrollmentsys.domain.courseEnroll.CourseEnrollRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final CourseEnrollRepository courseEnrollRepository;

    public Member joinMember(Member member){
        if (memberRepository.findByUserId(member.getUserId()).isPresent()){
            log.info("userId : {} 는 이미 있는 아이디 입니다.", member.getUserId());
            return null;
        }

        if (member.getRole() == Role.STUDENT){
            return studentRepository.save(new Student(member));
        }else if (member.getRole() == Role.PROFESSOR){
            return professorRepository.save(new Professor(member));
        }else {
            return memberRepository.save(member);
        }
    }

    public Member findMemberById(Long id){
        return memberRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 id를 가진 member가 없습니다.")
        );
    }
    public Member findMemberByUserId(String userId){
        return memberRepository.findByUserId(userId).orElseThrow(
                () -> new NoSuchElementException("해당 userId를 가진 member가 없습니다.")
        );
    }

    public Professor findProfessorByUserId(String userId){
        return professorRepository.findByUserId(userId).orElseThrow(
                () -> new NoSuchElementException("해당 userId를 가진 professor가 없습니다.")
        );
    }

    public Professor findProfessorById(Long id){
        return professorRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 userId를 가진 professor가 없습니다.")
        );
    }

    public Student findStudentByUserId(String userId){
        return studentRepository.findByUserId(userId).orElseThrow(
                () -> new NoSuchElementException("해당 userId를 가진 student가 없습니다.")
        );
    }

    public Student findStudentById(Long id){
        return studentRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 id를 가진 student가 없습니다.")
        );
    }
    public Professor deleteProfessor(String userId){
        Professor professor = findProfessorByUserId(userId);
        // 교수에게 배정된 강의 관계 해제
        for (Course course : professor.getCourseSet()){
            cancelCourseAssign(professor, course);
        }
        professorRepository.delete(professor);
        professor.setCourseSet(null);
        return professor;
    }

    public Student deleteStudent(String userId){
        Student student = findStudentByUserId(userId);
        for (CourseEnroll courseEnroll : student.getCourseEnrollSet()){
            cancelCourseEnroll(student, courseEnroll);
        }
        studentRepository.delete(student);
        return student;
    }

    public boolean cancelCourseEnroll(Student student, CourseEnroll courseEnroll){
        if (courseEnroll.getStudent() == student){
            Course course = courseEnroll.getCourse();
            course.setEnrollNum(course.getEnrollNum() - 1);
            courseEnrollRepository.delete(courseEnroll);
            student.getCourseEnrollSet().remove(courseEnroll);
            return true;
        }
        return false;
    }

    public boolean cancelCourseAssign(Professor professor, Course course){
        if (course.getProfessor() == professor){
            course.setProfessor(null);
            professor.getCourseSet().remove(course);
            return true;
        }
        return false;
    }

    public boolean assignCourse(Professor professor, Course course){
        if (course.getProfessor() != professor){
            Professor oldProfessor = course.getProfessor();
            if (oldProfessor != null){
                cancelCourseAssign(oldProfessor, course);
            }
            course.setProfessor(professor);
            professor.getCourseSet().add(course);
            return true;
        }
        return false;
    }

    public boolean enrollCourse(Student student, Course course){
        // 수강인원 초과
        if (course.getEnrollNum() >= course.getMaxNum())
            return false;

        Optional<CourseEnroll> courseEnrollOpt =
                courseEnrollRepository.findByStudentIdAndCourseId(student.getId(), course.getId());
        // 이미 신청된 과목인경우,
        if (courseEnrollOpt.isPresent())
            return false;

        CourseEnroll courseEnroll = CourseEnroll.builder()
                .course(course)
                .student(student)
                .build();
        courseEnrollRepository.save(courseEnroll);
        return true;
    }

    public List<Professor> findProfessorListStartWith(String words, int recommend_num){
        return professorRepository.findAll().stream()
                .filter((professor -> professor.getName().startsWith(words)))
                .limit(recommend_num)
                .toList();
    }

}
