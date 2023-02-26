package com.todrepus.enrollmentsys.web.admin.member;

import com.todrepus.enrollmentsys.domain.member.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JoinRequestDTO {
    @NotBlank
    private String userId;
    @NotBlank
    private String name;
    @NotBlank
    private String password;

    @NotBlank
    private String phoneNumber;
    @NotNull
    private Role role;

}
