package com.todrepus.enrollmentsys.web.admin.member.dto;

import com.todrepus.enrollmentsys.domain.department.Department;
import com.todrepus.enrollmentsys.domain.member.Professor;
import com.todrepus.enrollmentsys.web.admin.course.dto.CourseResponseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProfessorResponseDTO extends MemberResponseDTO{
    private Department department;
    private Integer year;
    private Set<CourseResponseDTO> courseResponseDTOSet = new HashSet<>();

    public ProfessorResponseDTO(Professor professor){
        super(professor);
        department = professor.getDepartment();
        year = professor.getYear();
        courseResponseDTOSet = professor.getCourseSet().stream()
                .map(CourseResponseDTO::new).collect(Collectors.toSet());
    }
}
