package com.takehometask.BetMachine.controller;

import com.takehometask.BetMachine.command.DetermineWinCommand;
import com.takehometask.BetMachine.command.GenerateServerNumberCommand;
import com.takehometask.BetMachine.model.BetRequestModel;
import com.takehometask.BetMachine.model.BetResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.RoundingMode;
import java.util.Objects;

@ControllerAdvice
@RestController("BetMachineRestController")
@Validated
@RequestMapping(path = "/game")
@RequiredArgsConstructor
public class BetMachineRestController {

    private final DetermineWinCommand determineWinCommand;
    private final GenerateServerNumberCommand generateServerNumberCommand;

    @PostMapping(value = "/bet")
    public BetResponseModel placeBet(@RequestBody @Valid BetRequestModel betRequest) {
        int serverNumber = generateServerNumberCommand.execute();

        return new BetResponseModel(
                determineWinCommand.execute(
                        betRequest.bet().setScale(2, RoundingMode.HALF_UP),
                        betRequest.number(),
                        serverNumber
                )
        );
    }

    @ExceptionHandler
    public ResponseEntity<String> handleArgumentNotValidException(MethodArgumentNotValidException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                String.format("Error: Field %s -- %s",
                Objects.requireNonNull(ex.getFieldError()).getField(),
                Objects.requireNonNull(ex.getFieldError()).getDefaultMessage()
                )
        );
    }

    @ExceptionHandler
    public ResponseEntity<String> handleMessageNotReadableException(HttpMessageNotReadableException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                "Invalid input provided"
        );
    }

}
