package com.vtheatre.service;

import javax.annotation.PostConstruct;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import com.vtheatre.data.model.PaymentRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.apiKey}")
    private String apiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = this.apiKey;
    }

    @Override
    public Charge Charge(PaymentRequest paymentRequest) {
        ChargeCreateParams params = ChargeCreateParams.builder().setAmount(paymentRequest.getAmount() * 100L)
                .setCurrency(paymentRequest.getCurrency()).setDescription(paymentRequest.getDescription())
                .setSource(paymentRequest.getTokenId()).build();

        Charge charge = null;
        try {
            charge = Charge.create(params);
        } catch (StripeException e) {
            e.printStackTrace();
        }

        return charge;
    }

}
