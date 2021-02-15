package com.vtheatre.controller;

import com.vtheatre.data.model.VerifyConfCodeResponse;
import com.vtheatre.data.model.TicketStatusRequest;
import com.vtheatre.data.model.VerifyConfCodeRequest;
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

    @PostMapping(value = "/verifyConfirmationCode")
    public ResponseEntity<VerifyConfCodeResponse> verifyConfirmationCode(@RequestBody VerifyConfCodeRequest verifyConfCodeRequest) {
        logger.info("Verifying confirmation code {}", verifyConfCodeRequest.getConfirmationCode());

        VerifyConfCodeResponse ticketResponse = ticketService.verifyConfirmationCode(verifyConfCodeRequest);

        logger.info("Does confirmation code exist: {}", ticketResponse.isExists());

        return new ResponseEntity<>(ticketResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/updateTicketStatus")
    public ResponseEntity<Boolean> updateTicketStatus(@RequestBody TicketStatusRequest ticketStatusRequest) {
        logger.info("Updating ticket status");

        boolean result = ticketService.updateTicketStatus(ticketStatusRequest);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
