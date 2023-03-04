package com.todrepus.enrollmentsys.web.course.dto;

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
    private List<CourseScheduleDTO> scheduleList;

}
