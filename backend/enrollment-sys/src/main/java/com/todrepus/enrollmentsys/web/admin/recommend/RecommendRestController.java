package com.todrepus.enrollmentsys.web.admin.recommend;

import com.todrepus.enrollmentsys.domain.course.CourseService;
import com.todrepus.enrollmentsys.domain.department.DepartmentService;
import com.todrepus.enrollmentsys.domain.member.MemberService;
import com.todrepus.enrollmentsys.domain.room.RoomService;
import com.todrepus.enrollmentsys.web.RestResponseDTO;
import com.todrepus.enrollmentsys.web.course.dto.CourseResponseDTO;
import com.todrepus.enrollmentsys.web.department.dto.DepartmentResponseDTO;
import com.todrepus.enrollmentsys.web.member.dto.ProfessorResponseDTO;
import com.todrepus.enrollmentsys.web.room.dto.RoomResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/recommend")
public class RecommendRestController {
    private final CourseService courseService;
    private final DepartmentService departmentService;
    private final RoomService roomService;
    private final MemberService memberService;

    private int RECOMMEND_NUM = 10;

    @GetMapping("/professors")
    public RestResponseDTO<List<ProfessorResponseDTO>> recommendProfessorList(@RequestParam String content){
        List<ProfessorResponseDTO> professorResponseDTOList =  memberService.findProfessorListStartWith(content,RECOMMEND_NUM).stream()
                .map(ProfessorResponseDTO::new)
                .toList();
        RestResponseDTO<List<ProfessorResponseDTO>> responseDTO = RestResponseDTO.getSuccessResponse("교수 추천");
        responseDTO.setData(professorResponseDTOList);
        log.debug("{}", professorResponseDTOList);
        return responseDTO;
    }

    @GetMapping("/departments")
    public RestResponseDTO<List<DepartmentResponseDTO>> recommendDepartmentList(@RequestParam String content){
        List<DepartmentResponseDTO> departmentResponseDTOList =  departmentService.findDepartmentListStartWith(content,RECOMMEND_NUM).stream()
                .map(DepartmentResponseDTO::new)
                .toList();
        RestResponseDTO<List<DepartmentResponseDTO>> responseDTO = RestResponseDTO.getSuccessResponse("학과 추천");
        responseDTO.setData(departmentResponseDTOList);
        log.debug("{}", departmentResponseDTOList);
        return responseDTO;
    }

    @GetMapping("/rooms")
    public RestResponseDTO<List<RoomResponseDTO>> recommendRoomList(@RequestParam String content){
        List<RoomResponseDTO> roomResponseDTOList =  roomService.findRoomListStartWith(content,RECOMMEND_NUM).stream()
                .map(RoomResponseDTO::new)
                .toList();
        RestResponseDTO<List<RoomResponseDTO>> responseDTO = RestResponseDTO.getSuccessResponse("강의실 추천");
        responseDTO.setData(roomResponseDTOList);
        log.debug("{}", roomResponseDTOList);
        return responseDTO;
    }
    @GetMapping("/courses")
    public RestResponseDTO<List<CourseResponseDTO>> recommendCourseList(@RequestParam String content){
        List<CourseResponseDTO> courseResponseDTOList =  courseService.findCourseListStartWith(content,RECOMMEND_NUM).stream()
                .map(CourseResponseDTO::new)
                .toList();
        RestResponseDTO<List<CourseResponseDTO>> responseDTO = RestResponseDTO.getSuccessResponse("강의 추천");
        responseDTO.setData(courseResponseDTOList);
        log.debug("{}", courseResponseDTOList);
        return responseDTO;
    }


}
