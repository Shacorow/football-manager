package com.football.manager.exceptions;

import lombok.Getter;

@Getter
public class ApplicationError {

    private final String message;

    private final boolean isTechnical;

    public ApplicationError(Exception exception) {
        this.message = exception.getMessage();
        isTechnical = exception instanceof RuntimeException;
    }
}
