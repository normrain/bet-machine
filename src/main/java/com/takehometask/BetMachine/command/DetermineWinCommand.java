package com.takehometask.BetMachine.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class DetermineWinCommand {

    public BigDecimal execute(BigDecimal bet, int number, int serverNumber) {

        if (serverNumber > number) {
            return BigDecimal.ZERO;
        }

        MathContext mc = new MathContext(4, RoundingMode.HALF_UP);
        BigDecimal intermediaryStep = new BigDecimal(99)
                .divide(
                        new BigDecimal(100).min(BigDecimal.valueOf(number)),
                        mc
                );

        return bet.multiply(intermediaryStep, mc);
    }
}
