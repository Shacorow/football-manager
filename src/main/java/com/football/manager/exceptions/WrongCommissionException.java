package com.football.manager.exceptions;

public class WrongCommissionException extends RuntimeException {
    public WrongCommissionException() {
        super("The commission should be between 0% and 10%.");
    }

}
