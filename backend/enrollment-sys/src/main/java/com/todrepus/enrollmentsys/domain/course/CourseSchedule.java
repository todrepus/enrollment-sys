package com.todrepus.enrollmentsys.domain.course;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="course_schedules")
public class CourseSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long id;

    @ManyToOne
    @JoinColumn(name="course_id")
    Course course;

    @Enumerated(EnumType.STRING)
    @Column(name="course_day")
    Day courseDay;

    @Column(name="course_hour_start")
    Integer courseHourStart;

    @Column(name="course_min_start")
    Integer courseMinStart;

    @Column(name="course_hour_end")
    Integer courseHourEnd;

    @Column(name="course_min_end")
    Integer courseMinEnd;

    @Builder
    public CourseSchedule(Day courseDay, Integer courseHourStart, Integer courseMinStart,
                          Integer courseHourEnd, Integer courseMinEnd){
        this.courseDay = courseDay;
        this.courseHourStart = courseHourStart;
        this.courseMinStart = courseMinStart;
        this.courseHourEnd = courseHourEnd;
        this.courseMinEnd = courseMinEnd;
    }
}
