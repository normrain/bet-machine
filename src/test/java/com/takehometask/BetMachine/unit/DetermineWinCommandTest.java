package com.takehometask.BetMachine.unit;

import com.takehometask.BetMachine.command.DetermineWinCommand;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;



@Tag("unit")
public class DetermineWinCommandTest {

    private final DetermineWinCommand determineWinCommand = new DetermineWinCommand();

    @Test
    public void withServerNumberSmaller_WinIsCalculatedCorrectly() {
        BigDecimal bet = new BigDecimal("40.5");
        int number = 50;
        int serverNumber = 1;

        BigDecimal result = determineWinCommand.execute(bet, number, serverNumber);

        assertThat(result).isEqualTo(BigDecimal.valueOf(80.19));
    }

    @Test
    public void withServerNumberGreater_WinIsNull() {
        BigDecimal bet = new BigDecimal("40.5");
        int number = 50;
        int serverNumber = 100;

        BigDecimal result = determineWinCommand.execute(bet, number, serverNumber);

        assertThat(result).isEqualTo(BigDecimal.valueOf(0));
    }
}
