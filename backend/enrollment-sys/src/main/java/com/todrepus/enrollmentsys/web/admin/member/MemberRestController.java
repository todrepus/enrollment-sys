package com.todrepus.enrollmentsys.web.admin.member;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.course.CourseService;
import com.todrepus.enrollmentsys.domain.courseEnroll.CourseEnroll;
import com.todrepus.enrollmentsys.domain.courseEnroll.CourseEnrollRepository;
import com.todrepus.enrollmentsys.domain.department.Department;
import com.todrepus.enrollmentsys.domain.department.DepartmentService;
import com.todrepus.enrollmentsys.domain.member.*;
import com.todrepus.enrollmentsys.web.admin.member.dto.*;
import com.todrepus.enrollmentsys.web.RestResponseDTO;
import com.todrepus.enrollmentsys.web.admin.AdminPageConst;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin // CORS 허용
@Slf4j
@RequiredArgsConstructor
@Transactional
@RestController
@RequestMapping("/api/admin/members")
public class MemberRestController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final CourseService courseService;
    private final DepartmentService departmentService;
    private final CourseEnrollRepository courseEnrollRepository;
    @GetMapping("")
    public RestResponseDTO<List<MemberResponseDTO>> getMembers(@RequestParam(required = false) Integer page) {
        if (page == null || page <= 0)
            page = 1;

        List<Member> members = memberRepository.findAll();
        int size = members.size();
        int maxPage = Math.ceilDiv(size, AdminPageConst.ELEMENT_NUM);
        int start = (page - 1) * AdminPageConst.ELEMENT_NUM;
        List<MemberResponseDTO> membersOnPage = members.stream().skip(start)
                .limit(AdminPageConst.ELEMENT_NUM)
                .map(MemberResponseDTO::new).toList();

        RestResponseDTO<List<MemberResponseDTO>> response = 
                RestResponseDTO.getSuccessResponse("멤버목록 조회");
        
        response.setData(membersOnPage);
        response.addParam("nowPage", page);
        response.addParam("maxPage", maxPage);
        return response;
    }

    @PostMapping("/add")
    public RestResponseDTO<MemberResponseDTO> addMember(@Validated @RequestBody JoinRequestDTO joinRequestDTO, BindingResult bindingResult,
                                                        HttpServletResponse httpServletResponse) {
        log.debug("{}",joinRequestDTO);
        if (bindingResult.hasErrors()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("회원 추가 실패");
        }

        Member newMember = Member.builder()
                .userId(joinRequestDTO.getUserId())
                .name(joinRequestDTO.getName())
                .password(joinRequestDTO.getPassword())
                .role(joinRequestDTO.getRole())
                .build();
        Member result = memberService.joinMember(newMember);
        log.debug("newMember {}", newMember);
        log.debug("result {}", result);
        if (result == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return RestResponseDTO.getInternalErrorResponse("가입에 실패하였습니다");
        } else {
            RestResponseDTO<MemberResponseDTO> response =
                    RestResponseDTO.getSuccessResponse("가입에 성공하였습니다.");

            MemberResponseDTO memberResponseDTO = new MemberResponseDTO(result);
            response.setData(memberResponseDTO);
            return response;
        }
    }

    @GetMapping("/{id}")
    public RestResponseDTO<MemberResponseDTO> getMember(@PathVariable Long id){
       Member foundMember = memberService.findMemberById(id);

        log.info("found : {}", foundMember);
        RestResponseDTO<MemberResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("회원 조회 성공");
        responseDTO.setData(new MemberResponseDTO(foundMember));
        return responseDTO;
    }

    @GetMapping("/students/{id}")
    public RestResponseDTO<StudentResponseDTO> getStudent(@PathVariable Long id){
        Student foundStudent = memberService.findStudentById(id);

        log.info("found : {}", foundStudent);
        RestResponseDTO<StudentResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("학생 조회 성공");
        responseDTO.setData(new StudentResponseDTO(foundStudent));
        return responseDTO;
    }

    @GetMapping("/professors/{id}")
    public RestResponseDTO<ProfessorResponseDTO> getProfessor(@PathVariable Long id){
        Professor foundProfessor = memberService.findProfessorById(id);

        log.info("found : {}", foundProfessor);
        RestResponseDTO<ProfessorResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("교수 조회 성공");
        responseDTO.setData(new ProfessorResponseDTO(foundProfessor));
        return responseDTO;
    }

    @PostMapping("/students/{id}/update")
    public RestResponseDTO<StudentResponseDTO> updateStudent(@PathVariable Long id, @Validated @RequestBody UpdateStudentDTO updateStudentDTO,
                                         BindingResult bindingResult, HttpServletResponse httpServletResponse){
        log.debug("{}", updateStudentDTO);
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("학생 업데이트 실패");
        }

        Student student = memberService.findStudentById(id);
        student.setName(updateStudentDTO.getName());
        student.setPassword(updateStudentDTO.getPassword());
        student.setPhoneNumber(updateStudentDTO.getPhoneNumber());

        if (updateStudentDTO.getSemeseter() != null)
            student.setSemester(updateStudentDTO.getSemeseter());
        if (updateStudentDTO.getState() != null)
            student.setState(updateStudentDTO.getState());

        Long departmentId = updateStudentDTO.getDepartmentId();
        if (departmentId != null) {
            Department department = departmentService.findDepartment(departmentId);
            student.setDepartment(department);
        }

        if (updateStudentDTO.getEnrollList() != null) {
            Set<CourseEnroll> updateCourseEnrollSet = updateStudentDTO.getEnrollList().stream().map(
                    courseEnrollUpdateDTO -> {
                        Course course = courseService.findCourse(courseEnrollUpdateDTO.getCourseId());
                        CourseEnroll enroll = CourseEnroll.builder()
                                .student(student)
                                .course(course)
                                .build();
                        enroll.setId(courseEnrollUpdateDTO.getId());
                        return enroll;
                    }
            ).collect(Collectors.toSet());

            Set<Long> enrollIdSet = student.getCourseEnrollSet().stream()
                    .map(CourseEnroll::getId)
                    .collect(Collectors.toSet());

            Set<Long> updateIdSet = updateCourseEnrollSet.stream()
                    .map(CourseEnroll::getId)
                    .collect(Collectors.toSet());

            // 차집합 연산으로, 제거대상 변경감지.
            enrollIdSet.removeAll(updateIdSet);
            enrollIdSet.forEach(courseEnrollRepository::deleteById);

            // 나머지 수강신청 저장 및 업데이트
            for (CourseEnroll courseEnroll : updateCourseEnrollSet) {
                courseEnrollRepository.save(courseEnroll);
            }

            student.setCourseEnrollSet(updateCourseEnrollSet);
        }

        RestResponseDTO<StudentResponseDTO> restResponseDTO =
                RestResponseDTO.getSuccessResponse("학생 업데이트 성공");
        restResponseDTO.setData(new StudentResponseDTO(student));

        log.debug("{}", restResponseDTO);
        return restResponseDTO;
    }

    @PostMapping("/professors/{id}/update")
    public RestResponseDTO<ProfessorResponseDTO> updateProfessor(@PathVariable Long id, @Validated @RequestBody UpdateProfessorDTO updateProfessorDTO,
                                         BindingResult bindingResult, HttpServletResponse httpServletResponse){
        log.debug("{}", updateProfessorDTO);
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("교수 업데이트 실패");
        }

        Professor professor = memberService.findProfessorById(id);

        professor.setName(updateProfessorDTO.getName());
        professor.setPassword(updateProfessorDTO.getPassword());
        professor.setPhoneNumber(updateProfessorDTO.getPhoneNumber());
        professor.setYear(updateProfessorDTO.getYear());

        Long departmentId = updateProfessorDTO.getDepartmentId();
        Department department = null;
        if (departmentId != null) {
            department = departmentService.findDepartment(departmentId);
        }
        professor.setDepartment(department);

        if (updateProfessorDTO.getCourseIdSet() != null) {
            Set<Long> updateIdSet = updateProfessorDTO.getCourseIdSet();
            Set<Course> courseSet = professor.getCourseSet();

            Iterator<Course> iter = courseSet.iterator();
            while (iter.hasNext()){
                Course course = iter.next();
                if (!updateIdSet.contains(course.getId())){
                    course.setProfessor(null); // 교수-강의 연관관계 끊기
                    iter.remove(); // iterator는 도중에 삭제해도 괜찮음.
                }else{ // 만약에 id가 존재한다면, 업데이트 대상에서 지워버린다.
                    updateIdSet.remove(course.getId());
                }
            }

            for (Long courseId : updateIdSet) {
                Course course = courseService.findCourse(courseId);
                memberService.assignCourse(professor, course);
            }
        }

        RestResponseDTO<ProfessorResponseDTO> restResponseDTO =
                RestResponseDTO.getSuccessResponse("교수 업데이트 성공");
        restResponseDTO.setData(new ProfessorResponseDTO(professor));

        return restResponseDTO;
    }

    @PostMapping("/{id}/delete")
    public RestResponseDTO<MemberResponseDTO> deleteMember(@PathVariable Long id, HttpServletResponse httpServletResponse) {
        Member member = memberService.findMemberById(id);
        if (member.getRole() == Role.STUDENT || member.getRole() == Role.PROFESSOR){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("요청된 id를 가진 회원의 유형이 잘못되었습니다.");
        }
        member = memberService.deleteMemberById(id);
        RestResponseDTO<MemberResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("회원 삭제에 성공");
        responseDTO.setData(new MemberResponseDTO(member));
        return responseDTO;
    }

    @PostMapping("/students/{id}/delete")
    public RestResponseDTO<StudentResponseDTO> deleteStudent(@PathVariable Long id, HttpServletResponse httpServletResponse){
        Student student = memberService.findStudentById(id);
        if (student.getRole() != Role.STUDENT){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("요청된 id를 가진 학생의 유형이 잘못되었습니다.");
        }
        student = memberService.deleteStudentById(id);

        RestResponseDTO<StudentResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("학생 삭제에 성공");
        responseDTO.setData(new StudentResponseDTO(student));
        return responseDTO;
    }

    @PostMapping("/professors/{id}/delete")
    public RestResponseDTO<ProfessorResponseDTO> deleteProfessor(@PathVariable Long id, HttpServletResponse httpServletResponse){
        Professor professor = memberService.findProfessorById(id);
        if (professor.getRole() != Role.PROFESSOR){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("요청된 id를 가진 교수의 유형이 잘못되었습니다.");
        }
        professor = memberService.deleteProfessorById(id);

        RestResponseDTO<ProfessorResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("교수 삭제에 성공");
        responseDTO.setData(new ProfessorResponseDTO(professor));
        return responseDTO;
    }

    @PostMapping("/professors/{userId}/courses/add")
    public RestResponseDTO<ProfessorResponseDTO> assignCourseToProfessor(@PathVariable String userId, Long courseId){
        Professor professor = memberService.findProfessorByUserId(userId);
        Course course = courseService.findCourse(courseId);
        memberService.assignCourse(professor, course);

        RestResponseDTO<ProfessorResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("강의 배정 성공");
        responseDTO.setData(new ProfessorResponseDTO(professor));
        return responseDTO;
    }

    @PostMapping("/students/{userId}/courses/add")
    public RestResponseDTO<StudentResponseDTO> enrollCourse(@PathVariable String userId, Long courseId, HttpServletResponse httpServletResponse){
        Student student = memberService.findStudentByUserId(userId);
        Course course = courseService.findCourse(courseId);
        boolean success = memberService.enrollCourse(student, course);

        if (!success){
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return RestResponseDTO.getInternalErrorResponse("강의등록 실패");
        }

        RestResponseDTO<StudentResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("강의등록 성공");
        responseDTO.setData(new StudentResponseDTO(student));
        return responseDTO;
    }
}
