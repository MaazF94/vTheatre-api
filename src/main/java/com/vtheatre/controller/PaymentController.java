package com.vtheatre.controller;

import com.vtheatre.data.model.PaymentRequest;
import com.vtheatre.data.model.PaymentResponse;
import com.vtheatre.service.PaymentService;

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
public class PaymentController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PaymentService paymentService;

    @PostMapping(value = "/completeAndroidPayment")
    public ResponseEntity<PaymentResponse> completeAndroidPayment(@RequestBody PaymentRequest paymentRequest) {
        logger.info("Received payment request");

        PaymentResponse paymentResponse = paymentService.processAndroidPayment(paymentRequest);

        logger.info("Sending payment response with result {}", paymentResponse.getConfirmed());

        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
