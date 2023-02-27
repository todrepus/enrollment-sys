package com.todrepus.enrollmentsys.web.admin.member;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.course.CourseService;
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
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{userId}")
    public RestResponseDTO<MemberResponseDTO> getMember(@PathVariable String userId){
       Member foundMember = memberService.findMemberByUserId(userId);

        log.info("found : {}", foundMember);
        RestResponseDTO<MemberResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("회원 조회 성공");
        responseDTO.setData(new MemberResponseDTO(foundMember));
        return responseDTO;
    }

    @GetMapping("/students/{userId}")
    public RestResponseDTO<StudentResponseDTO> getStudent(@PathVariable String userId){
        Student foundStudent = memberService.findStudentByUserId(userId);

        log.info("found : {}", foundStudent);
        RestResponseDTO<StudentResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("학생 조회 성공");
        responseDTO.setData(new StudentResponseDTO(foundStudent));
        return responseDTO;
    }

    @GetMapping("/professors/{userId}")
    public RestResponseDTO<ProfessorResponseDTO> getProfessor(@PathVariable String userId){
        Professor foundProfessor = memberService.findProfessorByUserId(userId);

        log.info("found : {}", foundProfessor);
        RestResponseDTO<ProfessorResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("교수 조회 성공");
        responseDTO.setData(new ProfessorResponseDTO(foundProfessor));
        return responseDTO;
    }

    @PostMapping("/students/{userId}/update")
    public RestResponseDTO<StudentResponseDTO> updateStudent(@PathVariable String userId, @Validated @RequestBody UpdateStudentDTO updateStudentDTO,
                                         BindingResult bindingResult, HttpServletResponse httpServletResponse){
        log.debug("{}", updateStudentDTO);
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("학생 업데이트 실패");
        }

        Student student = memberService.findStudentByUserId(userId);

        student.setName(updateStudentDTO.getName());
        student.setPassword(updateStudentDTO.getPassword());
        student.setPhoneNumber(updateStudentDTO.getPhoneNumber());
        student.setSemester(updateStudentDTO.getSemeseter());
        student.setState(updateStudentDTO.getState());

        Long departmentId = updateStudentDTO.getDepartmentId();
        Department department = null;
        if (departmentId != null) {
            department = departmentService.findDepartment(departmentId);
        }
        student.setDepartment(department);

        RestResponseDTO<StudentResponseDTO> restResponseDTO =
                RestResponseDTO.getSuccessResponse("학생 업데이트 성공");
        restResponseDTO.setData(new StudentResponseDTO(student));

        return restResponseDTO;
    }

    @PostMapping("/professors/{userId}/update")
    public RestResponseDTO<ProfessorResponseDTO> updateProfessor(@PathVariable String userId, @Validated @RequestBody UpdateProfessorDTO updateProfessorDTO,
                                         BindingResult bindingResult, HttpServletResponse httpServletResponse){
        log.debug("{}", updateProfessorDTO);
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("교수 업데이트 실패");
        }

        Professor professor = memberService.findProfessorByUserId(userId);

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

        RestResponseDTO<ProfessorResponseDTO> restResponseDTO =
                RestResponseDTO.getSuccessResponse("교수 업데이트 성공");
        restResponseDTO.setData(new ProfessorResponseDTO(professor));

        return restResponseDTO;
    }

    @PostMapping("/students/{userId}/delete")
    public RestResponseDTO<StudentResponseDTO> deleteStudent(@PathVariable String userId){
        Student student = memberService.deleteStudent(userId);
        RestResponseDTO<StudentResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("학생 삭제에 성공");
        responseDTO.setData(new StudentResponseDTO(student));
        return responseDTO;
    }

    @PostMapping("/professors/{userId}/delete")
    public RestResponseDTO<ProfessorResponseDTO> deleteProfessor(@PathVariable String userId){
        Professor professor = memberService.deleteProfessor(userId);
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
