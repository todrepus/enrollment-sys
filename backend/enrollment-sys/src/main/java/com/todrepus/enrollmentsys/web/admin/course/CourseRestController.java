package com.todrepus.enrollmentsys.web.admin.course;

import com.todrepus.enrollmentsys.domain.course.*;
import com.todrepus.enrollmentsys.domain.department.Department;
import com.todrepus.enrollmentsys.domain.department.DepartmentService;
import com.todrepus.enrollmentsys.domain.member.Member;
import com.todrepus.enrollmentsys.domain.member.MemberService;
import com.todrepus.enrollmentsys.domain.member.Professor;
import com.todrepus.enrollmentsys.domain.room.Room;
import com.todrepus.enrollmentsys.domain.room.RoomService;
import com.todrepus.enrollmentsys.web.RestResponseDTO;
import com.todrepus.enrollmentsys.web.RestState;
import com.todrepus.enrollmentsys.web.admin.AdminPageConst;
import com.todrepus.enrollmentsys.web.admin.member.MemberResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/admin/courses")
public class CourseRestController {
    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final RoomService roomService;
    private final DepartmentService departmentService;
    private final MemberService memberService;
    private final CourseScheduleRepository courseScheduleRepository;

    @GetMapping("")
    public RestResponseDTO getCourseList(Integer page){
        if (page == null || page <= 0){
            page = 1;
        }

        List<Course> courses = courseRepository.findAll();
        int size = courses.size();
        int maxPage = Math.ceilDiv(size, AdminPageConst.ELEMENT_NUM);
        int start = (page - 1) * AdminPageConst.ELEMENT_NUM;
        List<CourseResponseDTO> coursesOnPage = courses.stream().skip(start)
                .limit(AdminPageConst.ELEMENT_NUM)
                .map(CourseResponseDTO::new).toList();

        RestResponseDTO response = RestResponseDTO.builder()
                .state(RestState.OK)
                .build();

        response.addParam("data", coursesOnPage);
        response.addParam("maxPage", maxPage);
        return response;
    }

    @GetMapping("/{courseId}")
    public RestResponseDTO getCourse(@PathVariable Long courseId){
        Course course = courseService.findCourse(courseId);
        RestResponseDTO responseDTO = RestResponseDTO.builder()
                .state(RestState.OK)
                .message("조회")
                .build();
        responseDTO.addParam("course", new CourseResponseDTO(course));
        return responseDTO;
    }

    @PostMapping("/add")
    public RestResponseDTO addCourse(@Validated @RequestBody AddCourseDTO addCourseDTO,
                                     BindingResult bindingResult, HttpServletResponse httpServletResponse){
        log.debug("{}", addCourseDTO);
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.builder()
                    .state(RestState.FAIL)
                    .build();
        }

        String name = addCourseDTO.getName();
        Integer maxNum = addCourseDTO.getMaxNum();
        Integer enrollNum = 0;
        Room room = roomService.findRoom(addCourseDTO.getRoomId());
        Department department = departmentService.findDepartment(addCourseDTO.getDepartmentId());
        Professor professor = memberService.findProfessorById(addCourseDTO.getProfessorId());

        List<CourseSchedule> courseScheduleList = addCourseDTO.getCourseScheduleDTOList().stream().map(
                courseScheduleDTO ->
                    CourseSchedule.builder()
                            .courseHourEnd(courseScheduleDTO.getCourseHourEnd())
                            .courseMinEnd(courseScheduleDTO.getCourseMinEnd())
                            .courseMinStart(courseScheduleDTO.getCourseMinStart())
                            .courseHourStart(courseScheduleDTO.getCourseHourStart())
                            .courseDay(courseScheduleDTO.getCouresDay())
                            .build()
        ).toList();

        Course course = Course.builder()
                .name(name)
                .enrollNum(enrollNum)
                .maxNum(maxNum)
                .room(room)
                .department(department)
                .professor(professor)
                .build();

        courseRepository.save(course);
        courseScheduleList.forEach(
                courseSchedule -> {
                    courseSchedule.setCourse(course);
                    courseScheduleRepository.save(courseSchedule);
                    course.getCourseScheduleList().add(courseSchedule);
                }
        );

        RestResponseDTO responseDTO = RestResponseDTO.builder()
                .message("강의생성")
                .state(RestState.OK)
                .build();
        responseDTO.addParam("course", course);
        return responseDTO;
    }

    @PostMapping("/{courseId}/delete")
    public RestResponseDTO deleteCourse(@PathVariable Long courseId){
        Course course = courseService.findCourse(courseId);
        course = courseService.deleteCourse(course);
        RestResponseDTO responseDTO = RestResponseDTO.builder()
                .message("강의삭제")
                .state(RestState.OK)
                .build();
        responseDTO.addParam("course", course);
        return responseDTO;
    }

    @PostMapping("/{courseId}/update")
    public RestResponseDTO updateCourse(@PathVariable Long courseId, @Validated @RequestBody UpdateCourseDTO updateCourseDTO,
                                        BindingResult bindingResult, HttpServletResponse httpServletResponse){
        log.debug("{}", updateCourseDTO);
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.builder()
                    .state(RestState.FAIL)
                    .build();
        }

        // 강의 찾기
        Course course = courseService.findCourse(courseId);

        String name = updateCourseDTO.getName();
        Integer maxNum = updateCourseDTO.getMaxNum();

        // Join대상 객체들은 필수로 받도록 안함.
        // 강의실 관계
        if (updateCourseDTO.getRoomId() != null) {
            Room room = roomService.findRoom(updateCourseDTO.getRoomId());
            course.setRoom(room);
        }

        // 학과 관계
        if (updateCourseDTO.getDepartmentId() != null) {
            Department department = departmentService.findDepartment(updateCourseDTO.getDepartmentId());
            course.setDepartment(department);
        }
        
        // 교수 관계
        if (updateCourseDTO.getProfessorId() != null) {
            Professor professor = memberService.findProfessorById(updateCourseDTO.getProfessorId());
            course.setProfessor(professor);
        }
        
        // 강의 스케줄 리스트
        if (updateCourseDTO.getCourseScheduleDTOList() != null) {
            List<CourseSchedule> courseScheduleList = updateCourseDTO.getCourseScheduleDTOList().stream().map(
                    courseScheduleDTO -> {
                        CourseSchedule schedule = CourseSchedule.builder()
                                .courseHourEnd(courseScheduleDTO.getCourseHourEnd())
                                .courseMinEnd(courseScheduleDTO.getCourseMinEnd())
                                .courseMinStart(courseScheduleDTO.getCourseMinStart())
                                .courseHourStart(courseScheduleDTO.getCourseHourStart())
                                .courseDay(courseScheduleDTO.getCouresDay())
                                .build();
                        schedule.setId(courseScheduleDTO.getId());
                        return schedule;
                    }
            ).toList();
            Set<Long> courseScheduleIdSet = course.getCourseScheduleList().stream()
                    .map(CourseSchedule::getId)
                    .collect(Collectors.toSet());

            Set<Long> updateIdSet = courseScheduleList.stream()
                    .map(CourseSchedule::getId)
                    .collect(Collectors.toSet());

            // 차집합 연산으로, 제거대상 변경감지.
            courseScheduleIdSet.removeAll(updateIdSet);
            courseScheduleIdSet.forEach(courseScheduleRepository::deleteById); // 스케줄 제거

            // 나머지 스케줄 저장 및 업데이트
            for (CourseSchedule courseSchedule : courseScheduleList) {
                courseSchedule.setCourse(course);
                courseScheduleRepository.save(courseSchedule);
            }
            course.setCourseScheduleList(courseScheduleList);
        }

        // 업데이트 반영
        course.setName(name);
        course.setMaxNum(maxNum);

        RestResponseDTO responseDTO = RestResponseDTO.builder()
                .message("강의업데이트")
                .state(RestState.OK)
                .build();
        responseDTO.addParam("course", new CourseResponseDTO(course));
        return responseDTO;
    }

}
