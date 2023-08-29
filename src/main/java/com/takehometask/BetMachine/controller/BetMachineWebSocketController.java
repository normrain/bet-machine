package com.takehometask.BetMachine.controller;

import com.takehometask.BetMachine.command.DetermineWinCommand;
import com.takehometask.BetMachine.command.GenerateServerNumberCommand;
import com.takehometask.BetMachine.model.BetRequestModel;
import com.takehometask.BetMachine.model.BetResponseModel;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.math.BigDecimal;


@ControllerAdvice
@Controller("BetMachineWebSocketController")
@RequiredArgsConstructor
@Validated
public class BetMachineWebSocketController {

    private final DetermineWinCommand determineWinCommand;
    private final GenerateServerNumberCommand generateServerNumberCommand;

    @MessageMapping("/game")
    @SendTo("/topic/bets")
    public @ResponseBody BetResponseModel send(@Payload @Valid BetRequestModel message) throws Exception {
        int serverNumber = generateServerNumberCommand.execute();

        return new BetResponseModel(determineWinCommand.execute(message.bet(), message.number(), serverNumber));
    }

    @MessageExceptionHandler
    @SendTo(value = "/topic/error")
    public Exception handleException(Exception message){
        return new Exception(message.getMessage());
    }
}
