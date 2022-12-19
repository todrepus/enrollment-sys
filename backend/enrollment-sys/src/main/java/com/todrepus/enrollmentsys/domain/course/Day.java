package com.todrepus.enrollmentsys.domain.course;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Day {
    SUN,MON,TUE,WED,THU,FRI,SAT;

    @JsonCreator
    public static Day from(String s) {
        return Day.valueOf(s);
    }

    @JsonValue
    public String getValue() {
        return this.toString();
    }
}
