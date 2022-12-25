package com.todrepus.enrollmentsys.web.admin;

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
    private String email;
    @NotNull
    private Role role;

}
