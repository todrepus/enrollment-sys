package com.todrepus.enrollmentsys.web.admin.course.dto;

import com.todrepus.enrollmentsys.domain.course.CourseSchedule;
import com.todrepus.enrollmentsys.domain.course.CourseScheduleRepository;
import com.todrepus.enrollmentsys.domain.course.Day;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CourseScheduleResponseDTO {
    private Long id;
    private Day courseDay;
    private Integer courseHourStart;
    private Integer courseMinStart;
    private Integer courseHourEnd;
    private Integer courseMinEnd;

    public CourseScheduleResponseDTO(CourseSchedule schedule){
        id = schedule.getId();
        courseDay = schedule.getCourseDay();
        courseHourStart = schedule.getCourseHourStart();
        courseMinStart = schedule.getCourseMinStart();
        courseHourEnd = schedule.getCourseHourEnd();
        courseMinEnd = schedule.getCourseMinEnd();
    }
}
