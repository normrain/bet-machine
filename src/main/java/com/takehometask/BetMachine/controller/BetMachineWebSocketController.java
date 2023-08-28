package com.takehometask.BetMachine.controller;

import com.takehometask.BetMachine.command.DetermineWinCommand;
import com.takehometask.BetMachine.model.BetRequestModel;
import com.takehometask.BetMachine.model.BetResponseModel;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
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

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public @ResponseBody BetResponseModel send(@Payload @Valid BetRequestModel message) throws Exception {
        return new BetResponseModel(determineWinCommand.execute(message.bet(), message.number()));
    }
}
