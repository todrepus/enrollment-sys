package com.todrepus.enrollmentsys.web.admin.department;

import com.todrepus.enrollmentsys.domain.department.Department;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DepartmentResponseDTO {
    private Long id;
    private String name;

    public DepartmentResponseDTO(Department department){
        if (department == null)
            return;
        this.id = department.getId();
        this.name = department.getName();

    }

}
