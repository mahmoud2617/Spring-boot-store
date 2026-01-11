package com.mahmoud.project.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "carts", schema = "practice_schema")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    private UUID id;

    @Column(name = "date_created", insertable = false, updatable = false)
    private LocalDate dateCreated;

    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<CartItem> cartItems = new HashSet<>();

    @PrePersist
    public void generateRandomId() {
        if (this.id == null) {
            this.id = UuidCreator.getTimeOrderedEpoch();
        }
    }

    @Transient
    public BigDecimal getTotalCartPrice() {
        return cartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transient
    public CartItem getItem(Long productId) {
        return cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    @Transient
    public CartItem addItem(Product product) {
        CartItem cartItem = getItem(product.getId());

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCart(this);
            this.getCartItems().add(cartItem);
        }

        return cartItem;
    }

    @Transient
    public void removeItem(Long productId) {
        CartItem cartItem = getItem(productId);

        if (cartItem != null) {
            cartItems.remove(cartItem);
            cartItem.setCart(null);
        }
    }

    @Transient
    public void clear() {
        for (CartItem item : cartItems) {
            item.setCart(null);
        }

        cartItems.clear();
    }
}
