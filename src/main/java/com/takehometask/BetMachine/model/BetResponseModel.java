package com.takehometask.BetMachine.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public record BetResponseModel(
        @Schema(
                description = "The amount that was won, or 0 if the bet was lost",
                example = "80.19"
        )
        BigDecimal win
) {
}
