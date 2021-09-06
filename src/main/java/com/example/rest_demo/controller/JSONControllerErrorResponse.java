package com.example.rest_demo.controller;

import com.example.rest_demo.util.exception.JSONErrorResponse;
import com.example.rest_demo.util.exception.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class JSONControllerErrorResponse {
    @ExceptionHandler(JSONException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public JSONErrorResponse handleSecurityException(JSONException se) {
        JSONErrorResponse response = new JSONErrorResponse(se.getMessage());
        return response;
    }
}
