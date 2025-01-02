package com.football.manager.exceptions;

public class BudgetExceedException extends RuntimeException {

    public BudgetExceedException(String name) {
        super("Team with name: " + name + " doesn't have enough money to transfer this player!");
    }

}
