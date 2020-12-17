package com.vtheatre.service;

import com.stripe.model.Charge;
import com.vtheatre.data.entity.Showtime;
import com.vtheatre.data.entity.Ticket;

import org.springframework.stereotype.Service;

@Service
public interface TicketService {

    Ticket createTicket(String confirmationCode, Charge charge, Showtime showtime);
    
}
