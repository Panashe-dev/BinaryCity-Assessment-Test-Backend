package com.binary.city.assignmenttest.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(RunTimeExceptionPlaceHolder.class)
  public ResponseEntity<ErrorResponse> handleCustomException(RunTimeExceptionPlaceHolder ex) {

    ErrorResponse errorResponse = populateErrorResponse("500", "internal server error!","internal server error!");
    log.error("Something went wrong, Exception : " + ex.getMessage());
    ex.printStackTrace();
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

  }

  @ExceptionHandler(InvalidFormatException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(InvalidFormatException ex) {

    ErrorResponse errorResponse = populateErrorResponse("400", "invalid format!","invalid format!");
    log.error("Something went wrong, Exception : " + ex.getMessage());
    ex.printStackTrace();
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleCustomException(Exception ex) {

    ErrorResponse errorResponse = populateErrorResponse("400", "Something went wrong!","Something went wrong!");
    log.error("Something went wrong, Exception : " + ex.getMessage());
    ex.printStackTrace();
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

  }

  @ExceptionHandler(ContactNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(ContactNotFoundException ex) {

    ErrorResponse errorResponse = populateErrorResponse("400", "contact not found","contact not found");
    log.error("Membership number not found, Exception : " + ex.getMessage());
    ex.printStackTrace();
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

  }

  @ExceptionHandler(InvalidEmailException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(InvalidEmailException ex) {

    ErrorResponse errorResponse = populateErrorResponse("400", "invalid email!","invalid email!");
    log.error("Invalid email format, Exception : " + ex.getMessage());
    ex.printStackTrace();
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

  }

  public ErrorResponse populateErrorResponse(String code,String status ,String message) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCode(code);
    errorResponse.setStatus(status);
    errorResponse.setMessage(message);
    return errorResponse;
  }
}
