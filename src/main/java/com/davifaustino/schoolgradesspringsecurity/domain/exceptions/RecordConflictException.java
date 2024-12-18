package com.davifaustino.schoolgradesspringsecurity.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RecordConflictException extends RuntimeException {
    
    public RecordConflictException(String message) {
        super(message);
    }
}
