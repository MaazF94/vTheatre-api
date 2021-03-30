package com.vtheatre.controller;

import com.vtheatre.data.model.VerifyTicketResponse;
import com.vtheatre.data.model.receiptvalidation.ValidateReceiptRequest;

import java.util.List;

import com.vtheatre.data.model.MyTicketsRequest;
import com.vtheatre.data.model.MyTicketsResponse;
import com.vtheatre.data.model.PaymentRequest;
import com.vtheatre.data.model.PaymentResponse;
import com.vtheatre.data.model.TicketStatusRequest;
import com.vtheatre.data.model.VerifyTicketRequest;
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
public class TicketController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TicketService ticketService;

    @PostMapping(value = "/verifyTicket")
    public ResponseEntity<VerifyTicketResponse> verifyTicket(@RequestBody VerifyTicketRequest verifyConfCodeRequest) {
        logger.info("Verifying ticket for user {} showtime {} date chosen {} and movieId {}",
                verifyConfCodeRequest.getUsername(), verifyConfCodeRequest.getShowtime(),
                verifyConfCodeRequest.getChosenDate(), verifyConfCodeRequest.getMovieId());

        VerifyTicketResponse ticketResponse = ticketService.verifyTicket(verifyConfCodeRequest);

        logger.info("Does ticket exist: {}", ticketResponse.isExists());

        return new ResponseEntity<>(ticketResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/updateTicketStatus")
    public ResponseEntity<Boolean> updateTicketStatus(@RequestBody TicketStatusRequest ticketStatusRequest) {
        logger.info("Updating ticket status");

        boolean result = ticketService.updateTicketStatus(ticketStatusRequest);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // delete this after UI is live in next update
    @PostMapping(value = "/processIosPayment")
    public ResponseEntity<PaymentResponse> processIosPayment(@RequestBody PaymentRequest paymentRequest) {
        logger.info("Received payment request");

        PaymentResponse paymentResponse = ticketService.processIosPayment(paymentRequest);

        logger.info("Sending payment response with result {}", paymentResponse.getConfirmed());

        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/getTickets")
    public ResponseEntity<List<MyTicketsResponse>> getTickets(@RequestBody MyTicketsRequest myTicketsRequest) {
        logger.info("Retrieving tickets for user {}", myTicketsRequest.getUsername());

        List<MyTicketsResponse> myTicketsResponse = ticketService.getTickets(myTicketsRequest.getUsername());

        logger.info("Sending tickets response");

        return new ResponseEntity<>(myTicketsResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/validateReceipt")
    public ResponseEntity<Boolean> validateReceipt(@RequestBody ValidateReceiptRequest validateReceiptRequest) {
        logger.info("Received apple server notification");

        boolean verified = ticketService.validateReceipt(validateReceiptRequest);

        logger.info("Processed apple server notification");

        return new ResponseEntity<Boolean>(verified, HttpStatus.OK);
    }

}
