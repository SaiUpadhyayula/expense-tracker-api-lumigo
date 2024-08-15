package com.programming.techie.expensetracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExpenseAlreadyExistsException extends RuntimeException {

    public ExpenseAlreadyExistsException(String message) {
        super(message);
    }
}
