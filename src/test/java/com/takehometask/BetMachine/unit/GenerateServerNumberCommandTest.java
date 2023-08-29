package com.takehometask.BetMachine.unit;

import com.takehometask.BetMachine.command.GenerateServerNumberCommand;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
public class GenerateServerNumberCommandTest {

    private final GenerateServerNumberCommand generateServerNumberCommand = new GenerateServerNumberCommand();

    @RepeatedTest(100)
    public void numberStaysWithinRange(){

        int result = generateServerNumberCommand.execute();

        assertThat(result).isGreaterThanOrEqualTo(1);
        assertThat(result).isLessThanOrEqualTo(100);
    }
}
