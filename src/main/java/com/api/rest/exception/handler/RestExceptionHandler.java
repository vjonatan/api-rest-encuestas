package com.api.rest.exception.handler;

import com.api.rest.dto.ErrorDetailDTO;
import com.api.rest.dto.ValidationError;
import com.api.rest.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private MessageSource messageSource;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ErrorDetailDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest httpServletRequest){
        ErrorDetailDTO errorDetail = new ErrorDetailDTO();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Validación fallida");
        errorDetail.setDetail("La validación de entrada falló");
        errorDetail.setMessage(exception.getMessage());

        String requestPath = (String) httpServletRequest.getAttribute("javax.servlet.error.request_uri");

        if (requestPath == null) {
            requestPath = httpServletRequest.getRequestURI();
        }

        //se lista todos los campos que tienen errores
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            List<ValidationError> validationErrorList = errorDetail.getErrors().get(fieldError.getField());

            if (validationErrorList == null) {
                validationErrorList = new ArrayList<ValidationError>();
                //en el map se agrega el campo del error y la lista de errores que tiene
                errorDetail.getErrors().put(fieldError.getField(), validationErrorList);
            }

            ValidationError validationError = new ValidationError();
            validationError.setCode(fieldError.getCode());
            validationError.setMessage(messageSource.getMessage(fieldError, null));
            validationErrorList.add(validationError);

        }
        return errorDetail;
    }
}
