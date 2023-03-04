package com.todrepus.enrollmentsys.web.course.dto;

import com.todrepus.enrollmentsys.domain.course.CourseSchedule;
import com.todrepus.enrollmentsys.domain.course.Day;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CourseScheduleDTO {
    private Long id;

    @NotNull
    private Day day;
    @NotNull
    private Integer startHour;
    @NotNull
    private Integer startMin;
    @NotNull
    private Integer endHour;
    @NotNull
    private Integer endMin;

    @Builder
    public CourseScheduleDTO(Day courseDay, Integer courseHourStart, Integer courseMinStart, Integer courseHourEnd, Integer courseMinEnd) {
        this.day = courseDay;
        this.startHour = courseHourStart;
        this.startMin = courseMinStart;
        this.endHour = courseHourEnd;
        this.endMin = courseMinEnd;
    }

    public CourseScheduleDTO(CourseSchedule schedule){
        if (schedule == null)
            return;
        this.id = schedule.getId();
        this.day = schedule.getCourseDay();
        this.startHour = schedule.getCourseHourStart();
        this.startMin = schedule.getCourseMinStart();
        this.endHour = schedule.getCourseHourEnd();
        this.endMin = schedule.getCourseMinEnd();
    }


}
