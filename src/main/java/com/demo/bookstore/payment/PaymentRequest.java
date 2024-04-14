package com.demo.bookstore.payment;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class PaymentRequest {
    @Value("${account.name}")
    private String accountName;
    @Value("${bank.code}")
    private String bankCode;
    @Value("${bank.name}")
    private String bankName;
    @Value("${account.number}")
    private String accountNumber;

    private String amount;
}
