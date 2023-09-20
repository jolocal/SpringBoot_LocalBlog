package com.local.blog.handler;

import com.local.blog.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
/*    @ExceptionHandler(value = Exception.class)
    public CMRespDto<String> handleArgumentException(Exception e){
        return new CMRespDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    } //500*/

    @ExceptionHandler(value = CustomValidationApiException.class)
    public ResponseEntity<CMRespDto<?>> validationException(CustomValidationApiException e){
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST);
    }

}
