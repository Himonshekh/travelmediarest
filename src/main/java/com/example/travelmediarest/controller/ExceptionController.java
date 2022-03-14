package com.example.travelmediarest.controller;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String,String> handleException(Exception ex) {
        return Map.of("custom status?","failed", "error",ex.getMessage());
    }
}