package com.football.manager.controllers;


import com.football.manager.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler({PlayerDoesNotExistException.class})
    public ResponseEntity<ApplicationError> handleValidationException(PlayerDoesNotExistException playerDoesNotExistException) {
        return ResponseEntity.status(404).body(new ApplicationError(playerDoesNotExistException));
    }

    @ExceptionHandler({TeamAlreadyExistException.class})
    public ResponseEntity<ApplicationError> handleValidationException(TeamAlreadyExistException teamAlreadyExistException) {
        return ResponseEntity.status(409).body(new ApplicationError(teamAlreadyExistException));
    }

    @ExceptionHandler({TeamNotExistException.class})
    public ResponseEntity<ApplicationError> handleValidationException(TeamNotExistException teamDoesNotExistException) {
        return ResponseEntity.status(404).body(new ApplicationError(teamDoesNotExistException));
    }

    @ExceptionHandler({BudgetExceedException.class})
    public ResponseEntity<ApplicationError> handleValidationException(BudgetExceedException teamDoesNotHaveEnoughMoneyToTransferException) {
        return ResponseEntity.status(400).body(new ApplicationError(teamDoesNotHaveEnoughMoneyToTransferException));
    }

    @ExceptionHandler({WrongCommissionException.class})
    public ResponseEntity<ApplicationError> handleValidationException(WrongCommissionException wrongCommissionException) {
        return ResponseEntity.status(400).body(new ApplicationError(wrongCommissionException));
    }
}
