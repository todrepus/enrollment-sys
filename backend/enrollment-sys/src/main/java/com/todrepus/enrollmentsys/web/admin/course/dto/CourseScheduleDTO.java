package com.todrepus.enrollmentsys.web.admin.course.dto;

import com.todrepus.enrollmentsys.domain.course.Day;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseScheduleDTO {
    private Long id;
    @NotNull
    private Day couresDay;
    @NotNull
    private Integer courseHourStart;
    @NotNull
    private Integer courseMinStart;
    @NotNull
    private Integer courseHourEnd;
    @NotNull
    private Integer courseMinEnd;

}
