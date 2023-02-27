package com.todrepus.enrollmentsys.web.admin.course;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todrepus.enrollmentsys.domain.course.*;
import com.todrepus.enrollmentsys.domain.courseEnroll.CourseEnrollRepository;
import com.todrepus.enrollmentsys.domain.department.Department;
import com.todrepus.enrollmentsys.domain.department.DepartmentRepository;
import com.todrepus.enrollmentsys.domain.department.DepartmentService;
import com.todrepus.enrollmentsys.domain.member.Member;
import com.todrepus.enrollmentsys.domain.member.Professor;
import com.todrepus.enrollmentsys.domain.member.ProfessorRepository;
import com.todrepus.enrollmentsys.domain.member.Role;
import com.todrepus.enrollmentsys.domain.room.Room;
import com.todrepus.enrollmentsys.domain.room.RoomRepository;
import com.todrepus.enrollmentsys.domain.room.RoomService;
import com.todrepus.enrollmentsys.web.RestResponseDTO;
import com.todrepus.enrollmentsys.web.admin.course.dto.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class CourseRestControllerTest {
    @Autowired
    MockMvc mvc;


    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CourseScheduleRepository courseScheduleRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseEnrollRepository courseEnrollRepository;

    @Autowired
    private CourseService courseService;

    @BeforeEach
    public void setup(){
        this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .alwaysDo(print())
                .build();
    }

    @Test
    void addCourse() throws Exception{
        Room room = roomRepository.findAll().get(0);
        Department department = departmentRepository.findAll().get(0);
        Professor professor = professorRepository.findAll().get(0);

        Integer maxNum = 50;
        AddCourseDTO courseDTO = new AddCourseDTO();
        courseDTO.setName("오늘의강의");
        courseDTO.setMaxNum(maxNum);
        courseDTO.setRoomId(room.getId());
        courseDTO.setDepartmentId(department.getId());
        courseDTO.setProfessorId(professor.getId());

        List<CourseScheduleDTO> courseScheduleList = List.of(
                CourseScheduleDTO.builder()
                        .courseDay(Day.MON)
                        .courseHourStart(14)
                        .courseMinStart(30)
                        .courseHourEnd(16)
                        .courseMinEnd(30).build(),
                CourseScheduleDTO.builder()
                        .courseDay(Day.THU)
                        .courseHourStart(15)
                        .courseMinStart(30)
                        .courseHourEnd(17)
                        .courseMinEnd(30).build()
        );

        courseDTO.setCourseScheduleDTOList(courseScheduleList);

        String requestJson = objectMapper.writeValueAsString(courseDTO);
        MvcResult result = mvc.perform(post("/api/admin/courses/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        RestResponseDTO<CourseResponseDTO> responseDTO = objectMapper.readValue(
                content, new TypeReference<>() {
                });

        if (responseDTO == null || responseDTO.getData() == null)
            fail();

        CourseResponseDTO courseResponseDTO = responseDTO.getData();

        assertEquals(courseDTO.getName(), courseResponseDTO.getName());
        assertEquals(courseDTO.getMaxNum(), courseResponseDTO.getMaxNum());
        assertEquals(room.getLocation(), courseResponseDTO.getRoom().getLocation());
        assertEquals(room.getHo(), courseResponseDTO.getRoom().getHo());
        assertEquals(department.getName(), courseResponseDTO.getDepartment().getName());
        assertEquals(professor.getName(), courseResponseDTO.getProfessorName());

        for (int i=0; i<2; i++){
            CourseScheduleDTO dto = courseScheduleList.get(i);
            CourseScheduleResponseDTO actualDto = courseResponseDTO.getCourseScheduleList().get(i);
            assertEquals(dto.getCourseDay(), actualDto.getCourseDay());
            assertEquals(dto.getCourseHourStart(), actualDto.getCourseHourStart());
            assertEquals(dto.getCourseMinStart(), actualDto.getCourseMinStart());
            assertEquals(dto.getCourseHourEnd(), actualDto.getCourseHourEnd());
            assertEquals(dto.getCourseMinEnd(), actualDto.getCourseMinEnd());
        }

    }

    @Test
    void deleteCourse() throws Exception{
        Course course = courseRepository.findAll().get(0);
        ////////////////////////////////////////////////////////
        String postUrl = "/api/admin/courses/" + course.getId().toString() + "/delete";
        MvcResult result = mvc.perform(post(postUrl))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        RestResponseDTO<CourseResponseDTO> responseDTO = objectMapper.readValue(
                responseJson, new TypeReference<RestResponseDTO<CourseResponseDTO>>() {}
        );

        if (responseDTO == null || responseDTO.getData() == null)
            fail();

        //////////////////////////////////////////////////////////

        // 1. 강의 삭제가 되었는가?
        if (courseRepository.findById(course.getId()).isPresent())
            fail();

        // 2. 해당 강의와 관련된 강의신청(CourseEnroll) 목록이 없는가?
        assertEquals(courseEnrollRepository.findByCourseId(
                course.getId()).size(), 0);

        // 3. 해당 강의와 관련된 시간표(CourseSchedule) 목록이 없는가?
        assertEquals(courseScheduleRepository.findByCourseId(
                course.getId()).size(), 0);
    }

    @Test
    void updateCourse() throws Exception{
        Course course = courseRepository.findAll().get(0);

        Room room = Room.builder()
                .location("새로운강의실")
                .ho("110")
                .build();
        room = roomRepository.save(room);

        Department department = new Department();
        department.setName("새롭게시작하는학과");
        department = departmentRepository.save(department);

        Professor professor = new Professor(Member.builder()
                .name("나는교수")
                .userId("ftes1234f")
                .password("r1234")
                .phoneNumber("01012568901")
                .build());
        professor = professorRepository.save(professor);

        String updateName = "나만의강의123";
        Integer updateMaxNum = course.getMaxNum() + 1;

        UpdateCourseDTO updateCourseDTO = new UpdateCourseDTO();
        updateCourseDTO.setName(updateName);
        updateCourseDTO.setRoomId(room.getId());
        updateCourseDTO.setDepartmentId(department.getId());
        updateCourseDTO.setProfessorId(professor.getId());
        updateCourseDTO.setMaxNum(updateMaxNum);
        updateCourseDTO.setCourseScheduleDTOList(
                course.getCourseScheduleList().stream()
                        .map(CourseScheduleDTO::new)
                        .collect(Collectors.toList())
        );


        List<CourseScheduleDTO> updateCourseScheduleList = List.of(
                CourseScheduleDTO.builder()
                        .courseDay(Day.MON)
                        .courseHourStart(14)
                        .courseMinStart(30)
                        .courseHourEnd(16)
                        .courseMinEnd(30).build(),
                CourseScheduleDTO.builder()
                        .courseDay(Day.THU)
                        .courseHourStart(15)
                        .courseMinStart(30)
                        .courseHourEnd(17)
                        .courseMinEnd(30).build()
        );

        // 첫번째 강의스케줄은 삭제.
        updateCourseDTO.getCourseScheduleDTOList().remove(0);
        updateCourseDTO.getCourseScheduleDTOList().addAll(updateCourseScheduleList);

        String postUrl = "/api/admin/courses/" + course.getId() + "/update";
        String requestJson = objectMapper.writeValueAsString(updateCourseDTO);
        mvc.perform(post(postUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        Course foundCourse = courseService.findCourse(course.getId());

        assertEquals(updateName, foundCourse.getName());
        assertEquals(room.getLocation(), foundCourse.getRoom().getLocation());
        assertEquals(room.getHo(), foundCourse.getRoom().getHo());
        assertEquals(department.getName(), foundCourse.getDepartment().getName());
        assertEquals(professor.getName(), foundCourse.getProfessor().getName());
        assertEquals(professor.getId(), foundCourse.getProfessor().getId());
        assertEquals(updateMaxNum, foundCourse.getMaxNum());
        assertEquals(updateCourseDTO.getCourseScheduleDTOList().size(),
                foundCourse.getCourseScheduleList().size());

        for (int i=0; i<updateCourseDTO.getCourseScheduleDTOList().size(); ++i){
            CourseScheduleDTO expectDTO = updateCourseDTO.getCourseScheduleDTOList().get(i);
            CourseSchedule actual = foundCourse.getCourseScheduleList().get(i);

            assertEquals(expectDTO.getCourseHourStart(), actual.getCourseHourStart());
            assertEquals(expectDTO.getCourseMinStart(), actual.getCourseMinStart());
            assertEquals(expectDTO.getCourseHourEnd(), actual.getCourseHourEnd());
            assertEquals(expectDTO.getCourseMinEnd(), actual.getCourseMinEnd());
            assertEquals(expectDTO.getCourseDay(), actual.getCourseDay());
        }


        assertEquals(updateCourseDTO.getCourseScheduleDTOList().size(),
                courseScheduleRepository.findByCourseId(course.getId()).size());
    }
}