package com.vtheatre.controller;

import com.vtheatre.data.model.PaymentRequest;
import com.vtheatre.data.model.PaymentResponse;
import com.vtheatre.service.PaymentService;
import com.vtheatre.service.TicketService;

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

    @Autowired
    TicketService ticketService;

    @PostMapping(value = "/processAndroidPayment")
    public ResponseEntity<PaymentResponse> processAndroidPayment(@RequestBody PaymentRequest paymentRequest) {
        logger.info("Received payment request");

        PaymentResponse paymentResponse = paymentService.processAndroidPayment(paymentRequest);

        logger.info("Sending payment response with result {}", paymentResponse.getConfirmed());

        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/completeIosPayment")
    public ResponseEntity<PaymentResponse> completeIosPayment(@RequestBody PaymentRequest paymentRequest) {
        logger.info("Received payment request");

        PaymentResponse paymentResponse = ticketService.completeIosPayment(paymentRequest);

        logger.info("Sending payment response with result {}", paymentResponse.getConfirmed());

        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
