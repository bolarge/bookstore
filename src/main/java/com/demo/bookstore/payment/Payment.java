package com.demo.bookstore.payment;

import com.demo.bookstore.utils.BaseEntity;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Embeddable
public class Payment { //extends BaseEntity {
    private String referenceId;
    private BigDecimal totalAmount;
    private String itemDescription;
    private PaymentMode PaymentMode;
    private LocalDate paymentDate;
}
