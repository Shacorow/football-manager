package com.football.manager.exceptions;

public class PlayerHasTeamException extends RuntimeException {

    public PlayerHasTeamException(Long id) {
        super("Player with id: " + id + " has team. You can only transfer him!");
    }

}
