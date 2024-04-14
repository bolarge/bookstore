package com.demo.bookstore.payment;

import feign.Headers;
import feign.RequestLine;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "payWithClient", url = "https://qa.interswitchng.com",
        path = "/paymentgateway/api/v1/virtualaccounts/transaction")
public interface PaymentClient {

    @PostMapping("")
    @Headers("Content-Type: application/json")
    PaymentResponse transfer(@RequestBody PaymentRequest paymentRequest);
}
