package com.todrepus.enrollmentsys.web.admin.member;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.course.CourseRepository;
import com.todrepus.enrollmentsys.domain.course.CourseService;
import com.todrepus.enrollmentsys.domain.courseEnroll.CourseEnrollRepository;
import com.todrepus.enrollmentsys.domain.department.Department;
import com.todrepus.enrollmentsys.domain.department.DepartmentRepository;
import com.todrepus.enrollmentsys.domain.department.DepartmentService;
import com.todrepus.enrollmentsys.domain.member.*;
import com.todrepus.enrollmentsys.web.RestResponseDTO;
import com.todrepus.enrollmentsys.web.RestState;
import com.todrepus.enrollmentsys.web.admin.AdminPageConst;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
    public RestResponseDTO getMembers(@RequestParam(required = false) Integer page) {
        if (page == null || page <= 0)
            page = 1;

        List<Member> members = memberRepository.findAll();
        int size = members.size();
        int maxPage = Math.ceilDiv(size, AdminPageConst.ELEMENT_NUM);
        int start = (page - 1) * AdminPageConst.ELEMENT_NUM;
        List<MemberResponseDTO> membersOnPage = members.stream().skip(start)
                .limit(AdminPageConst.ELEMENT_NUM)
                .map(MemberResponseDTO::new).toList();

        RestResponseDTO response = RestResponseDTO.builder()
                .state(RestState.OK)
                .build();

        response.addParam("data", membersOnPage);
        response.addParam("maxPage", maxPage);
        return response;
    }

    @PostMapping("/add")
    public RestResponseDTO addMember(@Validated @RequestBody JoinRequestDTO joinRequestDTO, BindingResult bindingResult,
                                     HttpServletResponse httpServletResponse) {
        log.debug("{}",joinRequestDTO);
        if (bindingResult.hasErrors()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.builder()
                    .state(RestState.FAIL).build();
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
            return RestResponseDTO.builder()
                    .state(RestState.FAIL)
                    .message("가입에 실패하였습니다.")
                    .build();
        } else {
            RestResponseDTO response = RestResponseDTO.builder()
                    .state(RestState.OK)
                    .message("가입에 성공하였습니다.")
                    .build();

            MemberResponseDTO memberResponseDTO = new MemberResponseDTO(result);
            response.addParam("data", memberResponseDTO);
            return response;
        }
    }

    @GetMapping("/{userId}")
    public RestResponseDTO getMember(@PathVariable String userId){
        Member foundMember = memberService.findMember(userId);

        log.info("found : {}", foundMember);
        RestResponseDTO responseDTO = RestResponseDTO.builder()
                .state(RestState.OK)
                .message("조회")
                .build();
        responseDTO.addParam("member", foundMember);
        return responseDTO;
    }

    @PostMapping("/students/{userId}/update")
    public RestResponseDTO updateStudent(@PathVariable String userId, @Validated @RequestBody UpdateStudentDTO updateStudentDTO,
                                         BindingResult bindingResult, HttpServletResponse httpServletResponse){
        log.debug("{}", updateStudentDTO);
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.builder()
                    .state(RestState.FAIL)
                    .build();
        }

        Student student = memberService.findStudent(userId);

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

        RestResponseDTO restResponseDTO = RestResponseDTO.builder()
                .state(RestState.OK)
                .message("update에 성공했습니다.")
                .build();
        restResponseDTO.addParam("student", student);

        return restResponseDTO;
    }

    @PostMapping("/professors/{userId}/update")
    public RestResponseDTO updateProfessor(@PathVariable String userId, @Validated @RequestBody UpdateProfessorDTO updateProfessorDTO,
                                         BindingResult bindingResult, HttpServletResponse httpServletResponse){
        log.debug("{}", updateProfessorDTO);
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.builder()
                    .state(RestState.FAIL)
                    .build();
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

        RestResponseDTO restResponseDTO = RestResponseDTO.builder()
                .state(RestState.OK)
                .message("update에 성공했습니다.")
                .build();
        restResponseDTO.addParam("professor", professor);

        return restResponseDTO;
    }

    @PostMapping("/students/{userId}/delete")
    public RestResponseDTO deleteStudent(@PathVariable String userId){
        Student student = memberService.deleteStudent(userId);
        RestResponseDTO responseDTO = RestResponseDTO.builder()
                .state(RestState.OK)
                .message("delete에 성공했습니다.")
                .build();
        responseDTO.addParam("student", student);
        return responseDTO;
    }

    @PostMapping("/professors/{userId}/delete")
    public RestResponseDTO deleteProfessor(@PathVariable String userId){
        Professor professor = memberService.deleteProfessor(userId);
        RestResponseDTO responseDTO = RestResponseDTO.builder()
                .state(RestState.OK)
                .message("delete에 성공했습니다.")
                .build();
        responseDTO.addParam("professor", professor);
        return responseDTO;
    }

    @PostMapping("/professors/{userId}/courses/add")
    public RestResponseDTO assignCourseToProfessor(@PathVariable String userId, Long courseId){
        Professor professor = memberService.findProfessorByUserId(userId);
        Course course = courseService.findCourse(courseId);
        memberService.assignCourse(professor, course);

        RestResponseDTO responseDTO = RestResponseDTO.builder()
                .state(RestState.OK)
                .message("강의배정에 성공했습니다.")
                .build();
        responseDTO.addParam("professor", professor);
        return responseDTO;
    }

    @PostMapping("/students/{userId}/courses/add")
    public RestResponseDTO enrollCourse(@PathVariable String userId, Long courseId, HttpServletResponse httpServletResponse){
        Student student = memberService.findStudent(userId);
        Course course = courseService.findCourse(courseId);
        boolean success = memberService.enrollCourse(student, course);

        RestResponseDTO responseDTO = null;
        if (success){
            responseDTO = RestResponseDTO.builder()
                    .message("강의등록에 성공했습니다.")
                    .state(RestState.OK)
                    .build();
            responseDTO.addParam("student", student);
        }else{
            responseDTO = RestResponseDTO.builder()
                    .message("강의등록에 실패했습니다.")
                    .state(RestState.FAIL)
                    .build();
            responseDTO.addParam("student", student);
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return responseDTO;
    }
}
