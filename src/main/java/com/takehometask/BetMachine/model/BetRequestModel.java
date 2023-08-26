package com.takehometask.BetMachine.model;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Jacksonized
@Builder
public record BetRequestModel(
        @NotNull
        @Min(value = 1, message = "Number must greater than 0")
        @Max(value = 100, message = "Number must be less than 101")
        int number,
        @NotNull
        float bet
) {
}
