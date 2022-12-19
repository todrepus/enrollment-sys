package com.todrepus.enrollmentsys.domain.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Getter
public class CourseDate {
    private static final String TIME_SPLIT = "~";
    private static final CourseDate emptyCourseDate = new CourseDate(Day.SUN, Time.Empty(), Time.Empty());
    Day day;
    Time startTime;
    Time endTime;

    public static CourseDate Of(String courseDateString){
        String[] words = courseDateString.split(" ");
        if (words.length != 2)
            return CourseDate.Empty();

        String[] timeStrings = words[1].split(TIME_SPLIT);
        if (timeStrings.length != 2)
            return CourseDate.Empty();

        CourseDate result = null;
        try {
            Day day = Day.valueOf(words[0]);
            Time startTime = Time.Of(timeStrings[0]);
            Time endTime = Time.Of(timeStrings[1]);
            result = new CourseDate(day, startTime, endTime);
        }catch (Exception e){
            log.debug("CourseDate 변환 도중 실패하였음.");
            result = CourseDate.Empty();
        }
        return result;
    }
    public static CourseDate Empty(){
        return emptyCourseDate;
    }
    @Override
    public String toString() {
        return String.format("%s %s%s%s", day, startTime,TIME_SPLIT, endTime);
    }
}
