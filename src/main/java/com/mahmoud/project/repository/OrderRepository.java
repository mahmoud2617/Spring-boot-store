package com.mahmoud.project.repository;

import com.mahmoud.project.entity.Order;
import com.mahmoud.project.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "orderItems.product")
    @Query("SELECT o FROM Order o WHERE o.customer = :customer ORDER BY o.id")
    List<Order> findAllByCustomer(@Param("customer") User customer);

    @EntityGraph(attributePaths = "orderItems.product")
    @Query("SELECT o FROM Order o WHERE o.id = :orderId")
    Optional<Order> getOrderById(@Param("orderId") Long orderId);
}
