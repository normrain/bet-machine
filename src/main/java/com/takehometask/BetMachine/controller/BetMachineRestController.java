package com.takehometask.BetMachine.controller;

import com.takehometask.BetMachine.command.DetermineWinCommand;
import com.takehometask.BetMachine.model.BetRequestModel;
import com.takehometask.BetMachine.model.BetResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.RoundingMode;

@ControllerAdvice
@RestController("BetMachineRestController")
@Validated
@RequestMapping(path = "/game")
@RequiredArgsConstructor
public class BetMachineRestController {

    private final DetermineWinCommand determineWinCommand;
    @PostMapping(value = "/bet")
    public BetResponseModel placeBet(@RequestBody @Valid BetRequestModel betRequest) {

        return new BetResponseModel(
                determineWinCommand.execute(betRequest.bet().setScale(2, RoundingMode.HALF_UP), betRequest.number())
        );
    }
}
