package com.willian.financial_organizer.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateResourceException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public DuplicateResourceException(String s) {
        super(s);
    }
}
