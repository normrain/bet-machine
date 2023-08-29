package com.takehometask.BetMachine.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class GenerateServerNumberCommand {

    public int execute() {
        return ThreadLocalRandom.current().nextInt(1, 101);
    }
}
