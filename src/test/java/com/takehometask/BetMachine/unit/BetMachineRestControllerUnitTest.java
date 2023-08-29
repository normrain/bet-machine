package com.takehometask.BetMachine.unit;

import com.takehometask.BetMachine.command.DetermineWinCommand;
import com.takehometask.BetMachine.command.GenerateServerNumberCommand;
import com.takehometask.BetMachine.controller.BetMachineRestController;
import com.takehometask.BetMachine.model.BetRequestModel;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@Tag("unit")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BetMachineRestControllerUnitTest {

    private final GenerateServerNumberCommand generateServerNumberCommand = mock(GenerateServerNumberCommand.class);
    private final DetermineWinCommand determineWinCommand = mock(DetermineWinCommand.class);

    private final BetMachineRestController betMachineRestController = new BetMachineRestController(determineWinCommand, generateServerNumberCommand);

    @BeforeAll
    void setUp(){

    }

    @Test
    public void verifyAllStepsAreExecuted(){
        BetRequestModel betRequest = new BetRequestModel(
                1,
                BigDecimal.valueOf(40.5)
        );

        when(generateServerNumberCommand.execute()).thenReturn(1);
        when(determineWinCommand.execute(betRequest.bet(), betRequest.number(), 1)).thenReturn(BigDecimal.ONE);

        betMachineRestController.placeBet(betRequest);

        verify(generateServerNumberCommand).execute();
        verify(determineWinCommand).execute(betRequest.bet().setScale(2, RoundingMode.HALF_UP), betRequest.number(), 1);
    }
}
