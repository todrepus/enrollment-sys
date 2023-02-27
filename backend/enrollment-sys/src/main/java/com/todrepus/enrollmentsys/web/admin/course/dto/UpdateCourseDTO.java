package com.todrepus.enrollmentsys.web.admin.course.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UpdateCourseDTO {
    @NotEmpty
    private String name;

    private Long roomId;

    private Long departmentId;

    private Long professorId;

    @NotNull
    private Integer maxNum;

    private List<CourseScheduleDTO> courseScheduleDTOList;
}
