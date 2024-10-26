package com.api.rest.exception.handler;

import com.api.rest.dto.ErrorDetailDTO;
import com.api.rest.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest httpServletRequest) {
        ErrorDetailDTO errorDetail = new ErrorDetailDTO();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Recurso no encontrado");
        errorDetail.setDetail(exception.getClass().getName());
        errorDetail.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }

}
