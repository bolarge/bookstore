package com.demo.bookstore.payment;

import com.demo.bookstore.ordering.SalesOrder;
import com.demo.bookstore.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Payment extends BaseEntity {
    private String referenceId;
    private String totalAmount;
    private String itemDescription;
    @Enumerated(EnumType.STRING)
    private PaymentChannel PaymentChannel;
    private LocalDate paymentDate;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @JsonBackReference
    @ToString.Exclude
    @OneToOne
    private SalesOrder salesOrder;

    public Payment(String referenceId, String totalAmount, String itemDescription,
                   PaymentChannel paymentChannel, SalesOrder salesOrder) {
        this.referenceId = referenceId;
        this.totalAmount = totalAmount;
        this.itemDescription = itemDescription;
        PaymentChannel = paymentChannel;
        this.salesOrder = salesOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;
        if (!super.equals(o)) return false;
        return getReferenceId().equals(payment.getReferenceId()) && getSalesOrder().equals(payment.getSalesOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getReferenceId(), getSalesOrder());
    }
}
