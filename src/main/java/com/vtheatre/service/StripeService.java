package com.vtheatre.service;

import com.vtheatre.data.entity.Ticket;
import com.vtheatre.data.model.PaymentRequest;

import org.springframework.stereotype.Service;

@Service
public interface StripeService {

	Ticket Charge(PaymentRequest paymentRequest);

}
