package com.football.manager.exceptions;

public class TeamDoesNotHaveEnoughMoneyToTransferException extends RuntimeException {

    public TeamDoesNotHaveEnoughMoneyToTransferException(String name) {
        super("Team with name: " + name + " doesn't have enough money to transfer this player!");
    }

}
