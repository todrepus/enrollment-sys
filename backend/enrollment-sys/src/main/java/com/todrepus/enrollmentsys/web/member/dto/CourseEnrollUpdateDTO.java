package com.todrepus.enrollmentsys.web.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CourseEnrollUpdateDTO {
    Long id;
    @NotNull
    Long courseId;
        
}
