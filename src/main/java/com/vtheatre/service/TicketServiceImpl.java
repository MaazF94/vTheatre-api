package com.vtheatre.service;

import com.stripe.model.Charge;
import com.vtheatre.common.TicketStatusConstants;
import com.vtheatre.data.entity.Showtime;
import com.vtheatre.data.entity.Ticket;
import com.vtheatre.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket createTicket(String confirmationCode, Charge charge, Showtime showtime) {
        Ticket ticket = new Ticket();
        ticket.setChargeId(charge.getId());
        ticket.setConfirmationCode(confirmationCode);
        ticket.setShowtimeId(showtime.getShowtimeId());
        ticket.setStatus(TicketStatusConstants.ACTIVE);
        return ticketRepository.save(ticket);
    }

    
}
