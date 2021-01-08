package com.vtheatre.controller;

import com.vtheatre.data.model.PaymentRequest;
import com.vtheatre.data.model.PaymentResponse;
import com.vtheatre.service.StripeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StripeService stripeService;

    @PostMapping(value = "/completePayment")
    public ResponseEntity<PaymentResponse> completePayment(@RequestBody PaymentRequest paymentRequest) {
        logger.info("Inside StripeController with {}", paymentRequest);
        
        PaymentResponse paymentResponse = stripeService.charge(paymentRequest);

        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }

}
