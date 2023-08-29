package com.takehometask.BetMachine.unit;


import com.takehometask.BetMachine.command.DetermineWinCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.*;

@Tag("unit")
@RunWith(PowerMockRunner.class)
@PrepareForTest(ThreadLocalRandom.class)
public class DetermineWinCommandTest {

    private final RandomGenerator randomGenerator = mock(RandomGenerator.class);
    private final DetermineWinCommand determineWinCommand = new DetermineWinCommand(randomGenerator);

 @BeforeEach
    public void setUp(){

        //
    }
    @Test
    public void withValidNumbers(){
        when(randomGenerator.nextInt()).thenReturn(100);
        System.out.println(randomGenerator.nextInt());
        BigDecimal result = determineWinCommand.execute(BigDecimal.valueOf(40.5), 50);

        assertThat(result).isEqualTo(BigDecimal.valueOf(80.19));


    }
}
