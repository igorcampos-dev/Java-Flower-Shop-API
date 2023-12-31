package com.flowershop.back.domain.flower;

import com.flowershop.back.configuration.annotations.IsValid;
import com.flowershop.back.configuration.annotations.ValidBase64;

public record FlowerGetDatabase(@IsValid String filename,
                                @IsValid
                                @ValidBase64
                                String file) {
}
