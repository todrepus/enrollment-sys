package com.todrepus.enrollmentsys.domain.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum StudentState {
    ENROLLED("ENROLLED", "재학"),
    LEAVE("LEAVE", "휴학"),
    GRADUATED("GRADUATED", "졸업");
    
    private final String key;
    private final String title;
    private static final Map<String, StudentState> STUDENT_STATE_MAP = Stream.of(values())
            .collect(Collectors.toMap(StudentState::getTitle, Function.identity()));

    @JsonCreator
    public static StudentState resolve(String title){
        return Optional.ofNullable(STUDENT_STATE_MAP.get(title))
                .orElseThrow(() -> new IllegalArgumentException("Invalid value"));
    }

    @JsonValue
    public String toTitle(){
        return title;
    }
}
