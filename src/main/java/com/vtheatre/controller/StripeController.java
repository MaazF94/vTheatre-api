package com.vtheatre.controller;

import com.stripe.model.Charge;
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
    public ResponseEntity<String> completePayment(@RequestBody PaymentRequest paymentRequest) {

        Charge charge = stripeService.Charge(paymentRequest);

        return new ResponseEntity<>(charge.getId(), HttpStatus.OK);
    }

}
