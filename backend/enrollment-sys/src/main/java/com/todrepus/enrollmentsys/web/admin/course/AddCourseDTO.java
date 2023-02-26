package com.todrepus.enrollmentsys.web.admin.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AddCourseDTO {
    @NotEmpty
    private String name;
    @NotNull
    private Long roomId;
    @NotNull
    private Long departmentId;
    @NotNull
    private Long professorId;
    @NotNull
    private Integer maxNum;
    @NotNull
    private List<CourseScheduleDTO> courseScheduleDTOList;

}
