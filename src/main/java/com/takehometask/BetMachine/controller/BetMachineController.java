package com.takehometask.BetMachine.controller;

import com.takehometask.BetMachine.model.BetRequestModel;
import com.takehometask.BetMachine.model.BetResponseModel;

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
@Controller
public class BetMachineController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    @MessageExceptionHandler(MessageConversionException.class)
    public @ResponseBody BetResponseModel send(@Payload @Valid BetRequestModel message) throws Exception {
        BigDecimal test = message.bet().multiply(BigDecimal.valueOf(99 / ( 100 - message.number())));
        System.out.println(test);
        int serverNumber = (int) (Math.random() * 100);
        if(serverNumber < message.number()) {
            return new BetResponseModel(test);
        } else {
            return new BetResponseModel(BigDecimal.ZERO);
        }
    }
}
