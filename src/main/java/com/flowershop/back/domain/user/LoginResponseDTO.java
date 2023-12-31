package com.flowershop.back.domain.user;

import com.flowershop.back.configuration.annotations.IsValid;
import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponseDTO(
        @Schema(example = "Token JWT 256H")
        @IsValid
        String token,

        @Schema(example = "HvxZtx6R6JPntroYQ1dgo4281hgwB3p7zoM1yzX3mfyWHdVK3G")
        String hash) {
}
