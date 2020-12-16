package com.vtheatre.service;

import com.stripe.model.Charge;
import com.vtheatre.data.model.PaymentRequest;

import org.springframework.stereotype.Service;

@Service
public interface StripeService {

	Charge Charge(PaymentRequest paymentRequest);

}
