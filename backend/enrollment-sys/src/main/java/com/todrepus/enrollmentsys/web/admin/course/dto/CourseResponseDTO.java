package com.todrepus.enrollmentsys.web.admin.course.dto;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.course.CourseSchedule;
import com.todrepus.enrollmentsys.domain.department.Department;
import com.todrepus.enrollmentsys.domain.member.Professor;
import com.todrepus.enrollmentsys.domain.room.Room;
import com.todrepus.enrollmentsys.web.admin.room.dto.RoomResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CourseResponseDTO {
    private Long courseId;
    private String name;
    private RoomResponseDTO room;
    private List<CourseScheduleResponseDTO> courseScheduleList;
    private Department department;
    private Integer enrollNum;
    private Integer maxNum;
    private Long professorId;
    private String professorName;

    public CourseResponseDTO(Course course){
        courseId = course.getId();
        name = course.getName();
        room = new RoomResponseDTO(course.getRoom());
        if (course.getCourseScheduleList() != null) {
            courseScheduleList = course.getCourseScheduleList().stream()
                    .map(CourseScheduleResponseDTO::new)
                    .toList();
        }
        department = course.getDepartment();
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
