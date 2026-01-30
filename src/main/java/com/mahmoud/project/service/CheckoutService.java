package com.mahmoud.project.service;

import com.mahmoud.project.dto.CheckoutRequest;
import com.mahmoud.project.dto.CheckoutResponse;
import com.mahmoud.project.entity.Cart;
import com.mahmoud.project.entity.Order;
import com.mahmoud.project.exception.CartIsEmptyException;
import com.mahmoud.project.exception.CartNotFoundException;
import com.mahmoud.project.repository.CartRepository;
import com.mahmoud.project.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;

    public CheckoutResponse checkout(CheckoutRequest request) {
        Cart cart = cartRepository.findCartWithItems(request.getCartId()).orElseThrow(
                CartNotFoundException::new
        );

        if (cart.isEmpty()) {
            throw new CartIsEmptyException();
        }

        Order order = Order.placeAnOrder(cart, authService.getCurrentUser());
        orderRepository.save(order);

        cartService.deleteCartItems(cart.getId());

        return new CheckoutResponse(order.getId());
    }
}
