package com.local.blog.handler;

import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Getter
public class CustomValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Map<String,String> errorMap;

    public CustomValidationException(String message) {
        super(message);
    }

    public CustomValidationException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

}
