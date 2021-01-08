package com.vtheatre.service;

import com.stripe.model.Charge;
import com.vtheatre.common.TicketConstants;
import com.vtheatre.data.entity.Ticket;
import com.vtheatre.data.model.PaymentRequest;
import com.vtheatre.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket createTicket(String confirmationCode, Charge charge, PaymentRequest paymentRequest) {
        Ticket ticket = new Ticket();

        ticket.setChargeId(charge.getId());
        ticket.setConfirmationCode(confirmationCode);
        ticket.setShowtimeId(paymentRequest.getShowtime().getShowtimeId());
        ticket.setStatus(TicketConstants.ACTIVE);
        return ticketRepository.save(ticket);
    }

    @Override
    public boolean verifyConfirmationCode(String confirmationCode) {
        return ticketRepository.existsByConfirmationCode(confirmationCode);
    }
}
