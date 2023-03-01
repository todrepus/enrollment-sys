package com.todrepus.enrollmentsys.web.admin.member.dto;

import com.todrepus.enrollmentsys.domain.member.Role;
import com.todrepus.enrollmentsys.domain.member.StudentState;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UpdateStudentDTO {
    @NotEmpty
    private String name;
    @NotEmpty

    private String password;
    @NotEmpty

    private String phoneNumber;
    @NotNull
    private Role role;
    private Long semeseter;
    private Long departmentId;
    private StudentState state;
    private List<CourseEnrollUpdateDTO> enrollList;
}
