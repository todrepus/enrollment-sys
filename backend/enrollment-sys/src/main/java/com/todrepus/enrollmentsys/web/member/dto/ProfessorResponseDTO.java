package com.todrepus.enrollmentsys.web.member.dto;

import com.todrepus.enrollmentsys.domain.member.Professor;
import com.todrepus.enrollmentsys.web.course.dto.CourseResponseDTO;
import com.todrepus.enrollmentsys.web.department.dto.DepartmentResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProfessorResponseDTO extends MemberResponseDTO{
    private DepartmentResponseDTO department;
    private Integer year;
    private Set<CourseResponseDTO> courseSet = new HashSet<>();

    public ProfessorResponseDTO(Professor professor){
        super(professor);
        if (professor == null)
            return;
        department = new DepartmentResponseDTO(professor.getDepartment());
        year = professor.getYear();

        courseSet = professor.getCourseSet().stream()
                .map(CourseResponseDTO::new).collect(Collectors.toSet());
    }
}
