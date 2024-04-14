package com.demo.bookstore.payment;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class PaymentApiAuthentication {

    @Value("${client}")
    private String client;

    @Value("${secret}")
    private String secret;

    /*@Value("${bookstore.app.oauth.accessToken}")
    private String slackOauthAccessToken;*/

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(client, secret);
    }

/*    @Bean
    public RequestInterceptor bearerTokenRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                template.header("Authorization",
                        String.format("Bearer %s", slackOauthAccessToken));
            }
        };
    }*/
}
