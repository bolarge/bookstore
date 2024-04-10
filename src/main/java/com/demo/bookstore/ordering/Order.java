package com.demo.bookstore.ordering;

import com.demo.bookstore.crm.User;
import com.demo.bookstore.utils.NamedEntity;
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
public class Order extends NamedEntity {
    @OneToOne
    private User customer;
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
