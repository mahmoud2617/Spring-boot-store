package com.mahmoud.project.payments;

import com.mahmoud.project.entity.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest webhookRequest);
}
