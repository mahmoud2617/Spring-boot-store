package com.mahmoud.project.mapper;

import com.mahmoud.project.dto.CartDto;
import com.mahmoud.project.dto.CartItemDto;
import com.mahmoud.project.entity.Cart;
import com.mahmoud.project.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CartMapper {
    @Mapping(target = "items", source = "cartItems")
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalCartPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "item", source = "product")
    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
