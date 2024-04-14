package com.demo.bookstore.payment;

import lombok.Data;

@Data
public class PaymentResponse {
    private String merchantCode;
    private String payableCode;
    private boolean enabled;
    private String dateCreated;
    private String accountName;
    private String accountNumber;
    private String bankName;
    private String bankCode;

}
