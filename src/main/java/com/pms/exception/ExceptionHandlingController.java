package com.pms.exception;

import com.sun.xml.internal.ws.developer.ValidationErrorHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(EmployeeNotFoundException exception) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode("Employee Not Found");
        response.setErrorMessage(exception.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(ProjectNotFoundException exception) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode("Project Not Found");
        response.setErrorMessage(exception.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(StoryNotFoundException exception) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode("Story Not Found");
        response.setErrorMessage(exception.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(TaskNotFoundException exception) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode("Task Not Found");
        response.setErrorMessage(exception.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> invalidInput(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();

        Errors errors = result;
        ExceptionResponse response = new ExceptionResponse();

        List<String> errorlist = new ArrayList<String>();


        for (ObjectError objectError : errors.getAllErrors()) {
            errorlist.add(objectError.getDefaultMessage());
        }
        response.setErrorCode("Validation Error");
        response.setErrorMessage("invalid inputs");
        response.setErrors(errorlist);
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
    }
}
