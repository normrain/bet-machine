package com.takehometask.BetMachine.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class DetermineWinCommand {

    public BigDecimal execute(BigDecimal bet, int number) {
        int serverNumber = 1 + (int) (Math.random() * 100);
        BigDecimal convertedNumber = BigDecimal.valueOf(number);
        MathContext mc = new MathContext(4,RoundingMode.HALF_UP);
        if(serverNumber < number){
            BigDecimal help = new BigDecimal(99).divide(new BigDecimal(100).min(convertedNumber), mc);
            System.out.println(help);
            BigDecimal win = bet.multiply(help, mc);
            System.out.println(bet);
            System.out.println(win);
            return win;
        } else {

            return BigDecimal.ZERO;
        }

    }

}
