package com.demo.bookstore.ordering;

import com.demo.bookstore.crm.User;
import com.demo.bookstore.payment.Payment;
import com.demo.bookstore.payment.PaymentStatus;
import com.demo.bookstore.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    @ToString.Exclude
    @OneToOne
    private User customer;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private BigDecimal orderAmount;
    private ShoppingCart shoppingCart;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @JsonBackReference
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;



}
