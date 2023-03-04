package com.todrepus.enrollmentsys.web.member.dto;

import com.todrepus.enrollmentsys.domain.member.Role;
import com.todrepus.enrollmentsys.domain.member.StudentState;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateProfessorDTO {
    @NotEmpty
    private String name;
    @NotEmpty

    private String password;
    @NotEmpty

    private String phoneNumber;
    @NotNull
    private Role role;
    private Integer year;
    private Long departmentId;
    private Set<Long> courseIdSet;
}
