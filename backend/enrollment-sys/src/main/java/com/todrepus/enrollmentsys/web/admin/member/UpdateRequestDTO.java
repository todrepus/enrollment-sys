package com.todrepus.enrollmentsys.web.admin.member;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.member.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UpdateRequestDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    @NotNull
    private Role role;
    @NotNull
    private List<Long> courseIdList;
}

