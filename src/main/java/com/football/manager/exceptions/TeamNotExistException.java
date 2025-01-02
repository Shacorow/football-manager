package com.football.manager.exceptions;

public class TeamNotExistException extends RuntimeException {

    public TeamNotExistException(Long id) {
        super("Team with id: " + id + " doesn't exist!");
    }

}
