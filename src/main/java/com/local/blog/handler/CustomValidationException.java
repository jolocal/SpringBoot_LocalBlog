package com.local.blog.handler;

import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Getter
public class CustomValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int status;
    private Map<String,String> errorMap;

    public CustomValidationException(int status, String message, Map<String, String> errorMap) {
        super(message);
        this.status = status;
        this.errorMap = errorMap;
    }

}
