package com.mahmoud.project.service;

import com.mahmoud.project.dto.OrderDto;
import com.mahmoud.project.entity.Order;
import com.mahmoud.project.entity.User;
import com.mahmoud.project.exception.OrderNotFoundException;
import com.mahmoud.project.mapper.OrderMapper;
import com.mahmoud.project.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAllByCustomer(authService.getCurrentUser());
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(Long orderId) {
         Order order = orderRepository.getOrderById(orderId).orElseThrow(
                 OrderNotFoundException::new
         );

         User user = authService.getCurrentUser();

         if (!order.isPlacedBy(user)) {
             throw new AccessDeniedException("Access denied");
         }

         return orderMapper.toDto(order);
    }
}
