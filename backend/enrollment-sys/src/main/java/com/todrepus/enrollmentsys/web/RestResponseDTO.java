package com.todrepus.enrollmentsys.web;

import com.todrepus.enrollmentsys.web.RestState;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
public class RestResponseDTO {
    private RestState state;
    private String message;

    private final Map<String, Object> context = new HashMap<>();


    @Builder
    public RestResponseDTO(RestState state, String message){
        this.state = state;
        this.message = message;
    }

    public void addParam(String key, Object param){
        Object prevParam = context.put(key, param);
        log.debug("[duplicated] key: {}, prevParam: {}, nowParam: {}", key, prevParam, param);
    }


}
