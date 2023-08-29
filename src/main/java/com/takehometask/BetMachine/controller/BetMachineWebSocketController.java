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
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.Valid;
import java.util.Objects;


@Controller("BetMachineWebSocketController")
@ControllerAdvice
@RequiredArgsConstructor
@Validated
public class BetMachineWebSocketController {

    private final DetermineWinCommand determineWinCommand;
    private final GenerateServerNumberCommand generateServerNumberCommand;

    @MessageMapping("/game")
    @SendTo("/topic/bets")
    public @ResponseBody BetResponseModel send(@Payload @Valid BetRequestModel message){
        int serverNumber = generateServerNumberCommand.execute();

        return new BetResponseModel(determineWinCommand.execute(message.bet(), message.number(), serverNumber));
    }

    @MessageExceptionHandler(MethodArgumentNotValidException.class)
    @SendTo("/topic/error")
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        return String.format("Error: Field %s -- %s",
                Objects.requireNonNull(
                        Objects.requireNonNull(
                                ex.getBindingResult().getFieldError()
                        ).getField()
                ),
                Objects.requireNonNull(
                        Objects.requireNonNull(
                                ex.getBindingResult().getFieldError()
                        ).getDefaultMessage()
        ));
    }

    @MessageExceptionHandler(MessageConversionException.class)
    @SendTo("/topic/error")
    public String handleMessageNotReadableException(MessageConversionException ex) {

        return "Invalid input provided";
    }
}
