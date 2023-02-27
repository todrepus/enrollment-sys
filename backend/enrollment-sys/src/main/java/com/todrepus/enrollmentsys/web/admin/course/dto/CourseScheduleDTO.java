package com.todrepus.enrollmentsys.web.admin.course.dto;

import com.todrepus.enrollmentsys.domain.course.CourseSchedule;
import com.todrepus.enrollmentsys.domain.course.Day;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CourseScheduleDTO {
    private Long id;

    @NotNull
    private Day courseDay;
    @NotNull
    private Integer courseHourStart;
    @NotNull
    private Integer courseMinStart;
    @NotNull
    private Integer courseHourEnd;
    @NotNull
    private Integer courseMinEnd;

    @Builder
    public CourseScheduleDTO(Day courseDay, Integer courseHourStart, Integer courseMinStart, Integer courseHourEnd, Integer courseMinEnd) {
        this.courseDay = courseDay;
        this.courseHourStart = courseHourStart;
        this.courseMinStart = courseMinStart;
        this.courseHourEnd = courseHourEnd;
        this.courseMinEnd = courseMinEnd;
    }

    public CourseScheduleDTO(CourseSchedule schedule){
        this.id = schedule.getId();
        this.courseDay = schedule.getCourseDay();
        this.courseHourStart = schedule.getCourseHourStart();
        this.courseMinStart = schedule.getCourseMinStart();
        this.courseHourEnd = schedule.getCourseHourEnd();
        this.courseMinEnd = schedule.getCourseMinEnd();
    }


}
