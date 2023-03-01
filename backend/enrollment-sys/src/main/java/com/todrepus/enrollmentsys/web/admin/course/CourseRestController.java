package com.todrepus.enrollmentsys.web.admin.course;

import com.todrepus.enrollmentsys.domain.course.*;
import com.todrepus.enrollmentsys.domain.department.Department;
import com.todrepus.enrollmentsys.domain.department.DepartmentService;
import com.todrepus.enrollmentsys.domain.member.MemberService;
import com.todrepus.enrollmentsys.domain.member.Professor;
import com.todrepus.enrollmentsys.domain.room.Room;
import com.todrepus.enrollmentsys.domain.room.RoomService;
import com.todrepus.enrollmentsys.web.RestResponseDTO;
import com.todrepus.enrollmentsys.web.admin.AdminPageConst;
import com.todrepus.enrollmentsys.web.admin.course.dto.AddCourseDTO;
import com.todrepus.enrollmentsys.web.admin.course.dto.CourseResponseDTO;
import com.todrepus.enrollmentsys.web.admin.course.dto.UpdateCourseDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin // CORS 허용
@Transactional
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
    public RestResponseDTO<List<CourseResponseDTO>> getCourseList(@RequestParam(required = false) Integer page){
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

        RestResponseDTO<List<CourseResponseDTO>> response =
                RestResponseDTO.getSuccessResponse("강의 목록 조회");

        response.setData(coursesOnPage);
        response.addParam("nowPage", page);
        response.addParam("maxPage", maxPage);
        return response;
    }

    @GetMapping("/{courseId}")
    public RestResponseDTO<CourseResponseDTO> getCourse(@PathVariable Long courseId){
        Course course = courseService.findCourse(courseId);
        RestResponseDTO<CourseResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("강의 조회");

        responseDTO.setData(new CourseResponseDTO(course));
        return responseDTO;
    }

    @PostMapping("/add")
    public RestResponseDTO<CourseResponseDTO> addCourse(@Validated @RequestBody AddCourseDTO addCourseDTO,
                                     BindingResult bindingResult, HttpServletResponse httpServletResponse){
        log.debug("{}", addCourseDTO);
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("강의 생성 실패");
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
                            .courseDay(courseScheduleDTO.getCourseDay())
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
                    course.addSchedule(courseSchedule);
                    courseScheduleRepository.save(courseSchedule);
                }
        );

        RestResponseDTO<CourseResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("강의 생성");
        responseDTO.setData(new CourseResponseDTO(course));
        return responseDTO;
    }

    @PostMapping("/{courseId}/delete")
    public RestResponseDTO<CourseResponseDTO> deleteCourse(@PathVariable Long courseId){
        Course course = courseService.findCourse(courseId);
        course = courseService.deleteCourse(course);
        RestResponseDTO<CourseResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("강의 삭제");
        responseDTO.setData(new CourseResponseDTO(course));
        return responseDTO;
    }

    @PostMapping("/{courseId}/update")
    public RestResponseDTO<CourseResponseDTO> updateCourse(@PathVariable Long courseId, @Validated @RequestBody UpdateCourseDTO updateCourseDTO,
                                        BindingResult bindingResult, HttpServletResponse httpServletResponse){
        log.debug("{}", updateCourseDTO);
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("강의 업데이트 실패");
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
                                .courseDay(courseScheduleDTO.getCourseDay())
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

        RestResponseDTO<CourseResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("강의 업데이트 성공");
        responseDTO.setData(new CourseResponseDTO(course));
        return responseDTO;
    }

}
