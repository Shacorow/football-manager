package com.football.manager.exceptions;

import lombok.Getter;

@Getter
public class ApplicationError {

    private final String message;

    public ApplicationError(Exception exception) {
        this.message = exception.getMessage();
    }
}
