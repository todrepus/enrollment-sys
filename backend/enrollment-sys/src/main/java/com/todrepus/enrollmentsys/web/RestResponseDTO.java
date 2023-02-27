package com.todrepus.enrollmentsys.web;

import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Data
public class RestResponseDTO<T> {
    private int statusCode;
    private String message;
    private T data;

    private final Map<String, Object> context = new HashMap<>();

    private RestResponseDTO(){

    }

    public void addParam(String key, Object param){
        Object prevParam = context.put(key, param);
        if (prevParam != null)
            log.debug("[duplicated] key: {}, prevParam: {}, nowParam: {}", key, prevParam, param);
    }

    public static <S> RestResponseDTO<S> getSuccessResponse(String message){
        RestResponseDTO<S> responseDTO = new RestResponseDTO<>();
        responseDTO.setMessage(message);
        responseDTO.setStatusCode(HttpServletResponse.SC_OK);
        return responseDTO;
    }

    public static <S> RestResponseDTO<S> getInternalErrorResponse(String message){
        RestResponseDTO<S> responseDTO = new RestResponseDTO<>();
        responseDTO.setMessage(message);
        responseDTO.setStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return responseDTO;
    }

    public static <S> RestResponseDTO<S> getBadRequestResponse(String message){
        RestResponseDTO<S> responseDTO = new RestResponseDTO<>();
        responseDTO.setMessage(message);
        responseDTO.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        return responseDTO;
    }

    public static <S> RestResponseDTO<S> getResponse(String message, int stateCode){
        RestResponseDTO<S> responseDTO = new RestResponseDTO<>();
        responseDTO.setMessage(message);
        responseDTO.setStatusCode(stateCode);
        return responseDTO;
    }




}
