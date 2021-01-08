package com.vtheatre.service;

import com.vtheatre.data.model.PaymentRequest;
import com.vtheatre.data.model.PaymentResponse;

import org.springframework.stereotype.Service;

@Service
public interface StripeService {

	PaymentResponse charge(PaymentRequest paymentRequest);

}
