package com.vtheatre.controller;

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
    public ResponseEntity<Boolean> verifyConfirmationCode(@RequestBody String confirmationCode) {
        logger.info("Verifying confirmation code {}", confirmationCode);

        boolean exists = ticketService.verifyConfirmationCode(confirmationCode);

        logger.info("Confirmation code verified with result {}", exists);

        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
}
