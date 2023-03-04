package com.todrepus.enrollmentsys.web.department.dto;

import com.todrepus.enrollmentsys.domain.department.Department;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AddDepartmentDTO {
    @NotEmpty
    private String name;

}
