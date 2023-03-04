package com.todrepus.enrollmentsys.web.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDTO {
    @NotEmpty
    private String userId;

    @NotEmpty
    private String password;
}
