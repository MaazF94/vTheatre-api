package com.vtheatre.controller;

import com.vtheatre.data.entity.Ticket;
import com.vtheatre.data.model.PaymentRequest;
import com.vtheatre.service.StripeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vtheatre/v1")
public class StripeController {

    @Autowired
    StripeService stripeService;

    @PostMapping(value = "/completePayment")
    public ResponseEntity<Ticket> completePayment(@RequestBody PaymentRequest paymentRequest) {

        Ticket ticket = stripeService.Charge(paymentRequest);

        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

}
