package com.todrepus.enrollmentsys.web.admin.member.dto;

import com.todrepus.enrollmentsys.domain.department.Department;
import com.todrepus.enrollmentsys.domain.member.Student;
import com.todrepus.enrollmentsys.domain.member.StudentState;
import com.todrepus.enrollmentsys.web.admin.department.DepartmentResponseDTO;
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
    private DepartmentResponseDTO department;
    private Set<SimpleEnrollDTO> courseEnrollDTOSet = new HashSet<>();

    public StudentResponseDTO(Student student){
        super(student);
        semester = student.getSemester();
        state = student.getState();
        department = new DepartmentResponseDTO(student.getDepartment());
        courseEnrollDTOSet = student.getCourseEnrollSet().stream().map(
                courseEnroll -> new SimpleEnrollDTO(courseEnroll.getId(), courseEnroll.getCourse())
        ).collect(Collectors.toSet());

    }
}
