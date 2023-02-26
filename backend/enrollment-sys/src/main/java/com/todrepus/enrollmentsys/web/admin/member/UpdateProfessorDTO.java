package com.todrepus.enrollmentsys.web.admin.member;

import com.todrepus.enrollmentsys.domain.member.Role;
import com.todrepus.enrollmentsys.domain.member.StudentState;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProfessorDTO {
    @NotEmpty
    private String userId;
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
}
