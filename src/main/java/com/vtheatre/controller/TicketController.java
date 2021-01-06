package com.vtheatre.controller;

import com.vtheatre.service.TicketService;

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

    @Autowired
    TicketService ticketService;

    @PostMapping(value = "/verifyConfirmationCode")
    public ResponseEntity<Boolean> completePayment(@RequestBody String confirmationCode) {

        boolean exists = ticketService.verifyConfirmationCode(confirmationCode);

        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
}
