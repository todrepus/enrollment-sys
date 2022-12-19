package com.todrepus.enrollmentsys.domain.course;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Getter
public class Time {
    private static final Time emptyTime = new Time(0, 0);
    private Integer hour;
    private Integer min;

    public static Time Of(String timeString){
        String[] words = timeString.split(":");
        if (words.length != 2)
            return Time.Empty();

        Time time = null;
        try {
            Integer hour = Integer.parseInt(words[0]);
            Integer min = Integer.parseInt(words[1]);
            time = new Time(hour, min);
        }catch (Exception e){
            log.debug("Time.Of에서 parseInt 에러발생.");
            time = Time.Empty();
        }
        return time;
    }

    public static Time Empty(){
        return Time.emptyTime;
    }

    @Override
    public String toString() {
        return hour + ":" + min;
    }
}
