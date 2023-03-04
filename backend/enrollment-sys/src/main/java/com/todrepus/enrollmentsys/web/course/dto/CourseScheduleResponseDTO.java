package com.todrepus.enrollmentsys.web.course.dto;

import com.todrepus.enrollmentsys.domain.course.CourseSchedule;
import com.todrepus.enrollmentsys.domain.course.Day;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CourseScheduleResponseDTO {
    private Long id;
    private Day day;
    private Integer startHour;
    private Integer startMin;
    private Integer endHour;
    private Integer endMin;

    public CourseScheduleResponseDTO(CourseSchedule schedule){
        id = schedule.getId();
        day = schedule.getCourseDay();
        startHour = schedule.getCourseHourStart();
        startMin = schedule.getCourseMinStart();
        endHour = schedule.getCourseHourEnd();
        endMin = schedule.getCourseMinEnd();
    }
}
