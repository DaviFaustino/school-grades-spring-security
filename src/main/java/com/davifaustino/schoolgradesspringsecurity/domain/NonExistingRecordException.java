package com.davifaustino.schoolgradesspringsecurity.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NonExistingRecordException extends RuntimeException {
    
    public NonExistingRecordException(String message) {
        super(message);
    }
}
