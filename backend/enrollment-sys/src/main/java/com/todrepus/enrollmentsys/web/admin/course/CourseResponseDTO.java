package com.todrepus.enrollmentsys.web.admin.course;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.course.CourseSchedule;
import com.todrepus.enrollmentsys.domain.department.Department;
import com.todrepus.enrollmentsys.domain.member.Professor;
import com.todrepus.enrollmentsys.domain.room.Room;
import lombok.Data;

import java.util.List;

@Data
public class CourseResponseDTO {
    private Long courseId;
    private String name;
    private Room room;
    private List<CourseSchedule> courseScheduleList;
    private Department department;
    private Integer enrollNum;
    private Integer maxNum;
    private Long professorId;
    private String professorName;

    public CourseResponseDTO(Course course){
        courseId = course.getId();
        name = course.getName();
        room = course.getRoom();
        courseScheduleList = course.getCourseScheduleList();
        department = course.getDepartment();
        enrollNum = course.getEnrollNum();
        maxNum = course.getMaxNum();

        Professor professor = course.getProfessor();
        if (professor != null) {
            professorId = professor.getId();
            professorName = professor.getName();
        }
    }
}
