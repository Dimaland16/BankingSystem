package by.softclub.test.clientservice.controller;

import by.softclub.test.clientservice.exception.DuplicateEmailException;
import by.softclub.test.clientservice.exception.DuplicatePassportDataException;
import by.softclub.test.clientservice.exception.DuplicatePhoneNumberException;
import by.softclub.test.clientservice.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(DuplicateEmailException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(DuplicatePhoneNumberException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePhoneNumberException(DuplicatePhoneNumberException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(DuplicatePassportDataException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePassportDataException(DuplicatePassportDataException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        ErrorResponse errorResponse = new ErrorResponse(status.value(), message);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
