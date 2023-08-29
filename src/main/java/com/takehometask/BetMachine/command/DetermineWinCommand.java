package com.takehometask.BetMachine.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

@Component
@RequiredArgsConstructor
public class DetermineWinCommand {

    private final RandomGenerator randomGenerator;

    public BigDecimal execute(BigDecimal bet, int number) {
        int serverNumber = randomGenerator.nextInt(1,100+1);;

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
