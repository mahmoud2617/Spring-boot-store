package com.mahmoud.project.controller;

import com.mahmoud.project.dto.CartDto;
import com.mahmoud.project.dto.CartItemDto;
import com.mahmoud.project.dto.CartItemDtoRequest;
import com.mahmoud.project.dto.UpdateCartItemRequest;
import com.mahmoud.project.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Tag(name = "Cart")
@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {
    CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
    ) {
        CartDto cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId())
                        .toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addCartItem(
            @PathVariable(name = "cartId") UUID cartId,
            @Valid @RequestBody CartItemDtoRequest cartItemDtoRequest
    ) {
        CartItemDto response = cartService.addCartItem(cartId, cartItemDtoRequest);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable(name = "cartId") UUID cartId) {
        CartDto cartDto = cartService.getCart(cartId);

        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartItemDto> updateCartItem(
            @PathVariable(name = "cartId") UUID cartId,
            @PathVariable(name = "productId") Long productId,
            @Valid @RequestBody UpdateCartItemRequest cartItemRequest
    ) {
        CartItemDto cartItemDto = cartService.updateQuantity(cartId, productId, cartItemRequest);

        return ResponseEntity.ok(cartItemDto);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteItem(
            @PathVariable(name = "cartId") UUID cartId,
            @PathVariable(name = "productId") Long productId
    ) {
        cartService.deleteItem(cartId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable(name = "cartId") UUID cartId) {
        cartService.deleteCartItems(cartId);
        return ResponseEntity.noContent().build();
    }
}
