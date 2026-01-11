package com.mahmoud.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CartItemDtoRequest {
    @NotNull
    private Long productId;
}
