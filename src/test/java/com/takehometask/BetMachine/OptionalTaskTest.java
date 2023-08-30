package com.takehometask.BetMachine;

import com.takehometask.BetMachine.command.DetermineWinCommand;
import com.takehometask.BetMachine.command.GenerateServerNumberCommand;
import com.takehometask.BetMachine.model.BetRequestModel;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Tag("unit")
public class OptionalTaskTest {

    private static BigDecimal totalBet = BigDecimal.ZERO;
    @Test
    public void calculateRTP_runningOn24Threads_oneMillionGames() {
        ExecutorService pool = Executors.newFixedThreadPool(24);
        Map<Integer, TestData> testData = prepareTestData();

        for (Integer key : testData.keySet()) {
            totalBet = totalBet.add(testData.get(key).betRequest.bet());
            pool.submit(new GameRunnable(testData.get(key).serverNumber, testData.get(key).betRequest) );

        }
        pool.shutdown();

        BigDecimal rtpFraction = ((GameRunnable.totalWin.divide(totalBet, 2, RoundingMode.HALF_UP)));
        BigDecimal rtpPercent = rtpFraction.multiply(new BigDecimal("100"));

        System.out.println(String.format("After %d runs: Total money bet: %.2f, total money won: %.2f. RTP: %.2f %%",
                testData.size(),
                totalBet,
                GameRunnable.totalWin,
                rtpPercent
        ));
    }

    private static class GameRunnable implements Runnable {
        private final int serverNumber;
        private final BetRequestModel betRequest;
        private static BigDecimal totalWin = BigDecimal.ZERO;
        private final DetermineWinCommand determineWinCommand = new DetermineWinCommand();

        public GameRunnable(int serverNumber, BetRequestModel betRequest) {
            this.serverNumber = serverNumber;
            this.betRequest = betRequest;
        }

        private synchronized static void addToTotalWin(BigDecimal win) {
            totalWin = totalWin.add(win).setScale(2,RoundingMode.HALF_UP);
        }

        @Override
        public void run() {
            addToTotalWin(
                    determineWinCommand.execute(
                            betRequest.bet(),
                            betRequest.number(),
                            serverNumber
                    )
            );
        }
    }

    private Map<Integer, TestData> prepareTestData() {

        Map<Integer, TestData> result = new HashMap<>(1000000);
        GenerateServerNumberCommand generateServerNumberCommand = new GenerateServerNumberCommand();
        for (int i = 0; i < 1000000; i++) {
            result.put(i,
                    new TestData(
                            generateServerNumberCommand.execute(),
                            new BetRequestModel(
                                    ThreadLocalRandom.current().nextInt(1, 101),
                                    BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0, 1001))
                                            .setScale(2, RoundingMode.HALF_UP)
                            )
                    )
            );

        }
        return result;
    }

    @Setter
    @Getter
    public static class TestData {
        int serverNumber;
        BetRequestModel betRequest;

        public TestData(int serverNumber, BetRequestModel betRequest) {
            this.serverNumber = serverNumber;
            this.betRequest = betRequest;
        }
    }
}
