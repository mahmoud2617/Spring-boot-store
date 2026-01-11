package com.mahmoud.project.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class CartDto {
    private UUID id;
    private List<CartItemDto> items;
    private BigDecimal totalPrice;

    public CartDto(UUID id, List<CartItemDto> items, BigDecimal totalPrice) {
        this.id = id;
        this.items = (items == null)? new ArrayList<>() : items;
        this.totalPrice = (totalPrice == null)? BigDecimal.ZERO : totalPrice;
    }
}
