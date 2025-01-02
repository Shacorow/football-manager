package com.football.manager.exceptions;

public class PlayerDoesNotExistException extends RuntimeException {

    public PlayerDoesNotExistException(Long id) {
        super("Player with id: " + id + " doesn't exist!");
    }

}
