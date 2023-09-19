package com.local.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T> {
    private int status;
    private String message;
    T data;

    public ResponseDto(int status, T data) {
        this.status = status;
        this.data = data;
    }
}
