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
public enum Role {
    USER("ROLE_USER", "일반 사용자"),
    STUDENT("ROLE_STUDENT", "학생"),
    PROFESSOR("ROLE_PROFESSOR", "교수"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
    private static final Map<String, Role> ROLE_MAP = Stream.of(values())
            .collect(Collectors.toMap(Role::getTitle, Function.identity()));

    @JsonCreator
    public static Role resolve(String title){
        return Optional.ofNullable(ROLE_MAP.get(title))
                .orElseThrow(() -> new IllegalArgumentException("Invalid value"));
    }

    @JsonValue
    public String toTitle(){
        return title;
    }
}
