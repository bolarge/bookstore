package com.demo.bookstore.ordering;

import com.demo.bookstore.crm.User;
import com.demo.bookstore.payment.Payment;
import com.demo.bookstore.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "sales_orders")
@Entity
public class SalesOrder extends BaseEntity {
    @OneToOne
    private User customer;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private BigDecimal orderAmount;
    //@OneToOne
    //@JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private Payment payment;

}
