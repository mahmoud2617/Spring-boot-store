package com.mahmoud.project.service;

import com.mahmoud.project.dto.CartDto;
import com.mahmoud.project.dto.CartItemDto;
import com.mahmoud.project.dto.CartItemDtoRequest;
import com.mahmoud.project.dto.UpdateCartItemRequest;
import com.mahmoud.project.entity.Cart;
import com.mahmoud.project.entity.CartItem;
import com.mahmoud.project.entity.Product;
import com.mahmoud.project.exception.CartNotFoundException;
import com.mahmoud.project.exception.ProductNotFoundException;
import com.mahmoud.project.mapper.CartMapper;
import com.mahmoud.project.repository.CartRepository;
import com.mahmoud.project.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    CartRepository cartRepository;
    CartMapper cartMapper;
    ProductRepository productRepository;

    public CartDto createCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);

        return cartMapper.toDto(cart);
    }

    @Transactional
    public CartItemDto addCartItem(UUID cartId, CartItemDtoRequest cartItemDtoRequest) {
        Cart cart = cartRepository.findById(cartId).orElse(null);

        if (cart == null) {
            throw new CartNotFoundException();
        }

        Product product = productRepository
                .findById(cartItemDtoRequest.getProductId()).orElse(null);

        if (product == null) {
            throw new ProductNotFoundException();
        }

        CartItem cartItem = cart.addItem(product);
        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId) {
        Cart cart = cartRepository.findCartWithItems(cartId).orElse(null);

        if (cart == null) {
            throw new CartNotFoundException();
        }

        return cartMapper.toDto(cart);
    }

    @Transactional
    public CartItemDto updateQuantity(UUID cartId, Long productId, UpdateCartItemRequest request) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);

        CartItem cartItem = cart.getItem(productId);
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new ProductNotFoundException();
        }

        if (cartItem == null) {
            cartItem = new CartItem();
        }

        cartItem.setProduct(product);
        cartItem.setQuantity(request.getQuantity());
        cart.getCartItems().add(cartItem);
        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public void deleteItem(UUID cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);

        if (cart == null) {
            throw new CartNotFoundException();
        }

        cart.removeItem(productId);
    }

    public void deleteCartItems(UUID cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);

        if (cart == null) {
            throw new CartNotFoundException();
        }

        cart.clear();
    }
}
