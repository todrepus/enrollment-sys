package com.todrepus.enrollmentsys.web.course.dto;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.member.Professor;
import com.todrepus.enrollmentsys.web.department.dto.DepartmentResponseDTO;
import com.todrepus.enrollmentsys.web.room.dto.RoomResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CourseResponseDTO {
    private Long courseId;
    private String name;
    private RoomResponseDTO room;
    private List<CourseScheduleResponseDTO> scheduleList;
    private DepartmentResponseDTO department;
    private Integer enrollNum;
    private Integer maxNum;
    private Long professorId;
    private String professorName;

    public CourseResponseDTO(Course course){
        if (course == null )
            return;
        courseId = course.getId();
        name = course.getName();
        room = new RoomResponseDTO(course.getRoom());
        if (course.getCourseScheduleList() != null) {
            scheduleList = course.getCourseScheduleList().stream()
                    .map(CourseScheduleResponseDTO::new)
                    .toList();
        }
        department = new DepartmentResponseDTO(course.getDepartment());
        enrollNum = course.getEnrollNum();
        maxNum = course.getMaxNum();

        Professor professor = course.getProfessor();
        if (professor != null) {
            professorId = professor.getId();
            professorName = professor.getName();
        }
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof CourseResponseDTO courseResponseDTO))
            return false;
        return this.courseId.equals(courseResponseDTO.courseId) && this.name.equals(courseResponseDTO.name)
                && this.room.equals(courseResponseDTO.room) && this.department.getName().equals(courseResponseDTO.department.getName())
                && this.enrollNum.equals(courseResponseDTO.enrollNum) && this.maxNum.equals((courseResponseDTO.maxNum))
                && this.professorId.equals(courseResponseDTO.professorId) && this.professorName.equals(courseResponseDTO.professorName);
    }
}
