package com.mahmoud.project.payments;

import com.mahmoud.project.entity.Cart;
import com.mahmoud.project.entity.Order;
import com.mahmoud.project.exception.CartIsEmptyException;
import com.mahmoud.project.exception.CartNotFoundException;
import com.mahmoud.project.exception.PaymentException;
import com.mahmoud.project.repository.CartRepository;
import com.mahmoud.project.repository.OrderRepository;
import com.mahmoud.project.service.AuthService;
import com.mahmoud.project.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        Cart cart = cartRepository.findCartWithItems(request.getCartId()).orElseThrow(
                CartNotFoundException::new
        );

        if (cart.isEmpty()) {
            throw new CartIsEmptyException();
        }

        Order order = Order.placeAnOrder(cart, authService.getCurrentUser());
        orderRepository.save(order);

        try {
            CheckoutSession checkoutSession = paymentGateway.createCheckoutSession(order);

            cartService.deleteCartItems(cart.getId());

            return new CheckoutResponse(order.getId(), checkoutSession.getCheckoutUrl());
        } catch (PaymentException ex) {
            throw new PaymentException();
        }
    }

    public void handleWebhookEvent(WebhookRequest webhookRequest) {
        paymentGateway
            .parseWebhookRequest(webhookRequest)
            .ifPresent(paymentResult -> {
                Order order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                order.setStatus(paymentResult.getPaymentStatus());
                orderRepository.save(order);
            });
    }
}
