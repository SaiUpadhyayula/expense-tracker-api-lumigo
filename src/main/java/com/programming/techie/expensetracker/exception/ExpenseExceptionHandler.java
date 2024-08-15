package com.programming.techie.expensetracker.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class ExpenseExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String INVALID_REQUEST_ARGUMENT = "Invalid request argument";

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleExpenseNotFoundException(ExpenseNotFoundException ex, WebRequest request) {
        ExceptionDTO errorDetails = new ExceptionDTO(ex.getMessage(),
                extractPath(request.getDescription(false)));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpenseAlreadyExistsException.class)
    public ResponseEntity<ExceptionDTO> handleClientAlreadyExistsException(ExpenseAlreadyExistsException ex, WebRequest request) {
        ExceptionDTO errorDetails = new ExceptionDTO(ex.getMessage(),
                extractPath(request.getDescription(false)));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        String errorString = String.join(", ", errors);
        ExceptionDTO apiError = new ExceptionDTO(errorString, extractPath(request.getDescription(false)));
        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    /* This exception is thrown when a client sends a request with an illegal request argument e.g. invalid data type etc. */
    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleIllegalArgument(RuntimeException ex, WebRequest request) {
        ExceptionDTO apiError = new ExceptionDTO(
                INVALID_REQUEST_ARGUMENT, extractPath(request.getDescription(false)));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> fallBack(Exception ex, WebRequest request) {
        ExceptionDTO errorDetails = new ExceptionDTO(ex.getMessage(),
                extractPath(request.getDescription(false)));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String extractPath(String originalPath) {
        // Assuming "uri=" is always present, find its position and extract the path by removing "uri="
        int uriIndex = originalPath.indexOf("uri=");
        if (uriIndex != -1) {
            return originalPath.substring(uriIndex + 4);
        } else {
            return originalPath;
        }
    }
}
