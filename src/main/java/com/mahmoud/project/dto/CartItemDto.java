package com.mahmoud.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class CartItemDto {
    private CartProductDto cartProductDto;
    private Integer quantity;
    private BigDecimal totalPrice;
}
