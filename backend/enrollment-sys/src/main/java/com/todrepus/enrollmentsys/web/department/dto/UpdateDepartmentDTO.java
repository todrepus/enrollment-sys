package com.todrepus.enrollmentsys.web.department.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdateDepartmentDTO {
    @NotEmpty
    private String name;

}
