package com.football.manager.exceptions;

public class TeamAlreadyExistException extends RuntimeException {
    public TeamAlreadyExistException(String name) {
        super("Team with name: " + name +" already exists!");
    }

}
