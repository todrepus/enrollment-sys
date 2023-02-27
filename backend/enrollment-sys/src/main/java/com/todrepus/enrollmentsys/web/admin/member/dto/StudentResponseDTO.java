package com.todrepus.enrollmentsys.web.admin.member.dto;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.department.Department;
import com.todrepus.enrollmentsys.domain.member.Student;
import com.todrepus.enrollmentsys.domain.member.StudentState;
import com.todrepus.enrollmentsys.web.admin.course.dto.CourseResponseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class StudentResponseDTO extends MemberResponseDTO {
    private Long semester;
    private StudentState state;
    private Department department;
    private Set<SimpleEnrollDTO> courseEnrollDTOSet = new HashSet<>();

    @Data
    public class SimpleEnrollDTO {
        private Long id;
        private CourseResponseDTO courseResponseDTO;

        public SimpleEnrollDTO(Long id, Course course){
            this.id = id;
            this.courseResponseDTO = new CourseResponseDTO(course);
        }
    }

    public StudentResponseDTO(Student student){
        super(student);
        semester = student.getSemester();
        state = student.getState();
        department = student.getDepartment();
        courseEnrollDTOSet = student.getCourseEnrollSet().stream().map(
                courseEnroll -> new SimpleEnrollDTO(courseEnroll.getId(), courseEnroll.getCourse())
        ).collect(Collectors.toSet());

    }
}
