package com.takehometask.BetMachine.model;


import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Validated
public record BetRequestModel(

        @Schema(
        description = "The number chosen for the game",
        example = "50"
        )
        @NotNull
        @Min(value = 1, message = "Number must greater than 0")
        @Max(value = 100, message = "Number must be less than 101")
        int number,
        @Schema(
                description = "The bet place",
                example = "40.5"
        )
        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal bet
) {
}
