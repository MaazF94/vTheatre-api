package com.vtheatre.service;

import java.util.Date;
import java.util.Optional;

import com.stripe.model.Charge;
import com.vtheatre.common.TicketConstants;
import com.vtheatre.data.entity.Ticket;
import com.vtheatre.data.model.PaymentRequest;
import com.vtheatre.data.model.PaymentResponse;
import com.vtheatre.data.model.TicketStatusRequest;
import com.vtheatre.data.model.VerifyTicketRequest;
import com.vtheatre.data.model.VerifyTicketResponse;
import com.vtheatre.repository.TicketRepository;
import com.vtheatre.util.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketServiceImpl implements TicketService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket createTicket(Charge charge, PaymentRequest paymentRequest) {
        logger.info("Creating ticket");

        Ticket ticket = new Ticket();
        Date chosenDate = DateUtils.transformDateFromString(paymentRequest.getChosenDate());

        ticket.setChargeId(charge.getId());
        ticket.setShowtimeId(paymentRequest.getShowtime().getShowtimeId());
        ticket.setStatus(TicketConstants.ACTIVE);
        ticket.setChosenDate(chosenDate);
        ticket.setMovieId(paymentRequest.getMovie().getMovieId());
        ticket.setUsername(paymentRequest.getUsername());
        return ticketRepository.save(ticket);
    }

    @Override
    public VerifyTicketResponse verifyTicket(VerifyTicketRequest verifyConfCodeRequest) {
        VerifyTicketResponse ticketResponse = new VerifyTicketResponse();
        Date chosenDate = DateUtils.transformDateFromString(verifyConfCodeRequest.getChosenDate());

        Optional<Ticket> ticket = ticketRepository.findByUsernameAndShowtimeAndChosenDate(
                verifyConfCodeRequest.getUsername(), verifyConfCodeRequest.getShowtime(), chosenDate);

        if (ticket.isPresent()) {
            ticketResponse.setStatus(ticket.get().getStatus());
            ticketResponse.setExists(true);
        } else {
            ticketResponse.setExists(false);
        }

        return ticketResponse;
    }

    @Override
    public boolean updateTicketStatus(TicketStatusRequest ticketStatusRequest) {
        logger.info("Finding ticket for user {}", ticketStatusRequest.getUsername());

        Date chosenDate = DateUtils.transformDateFromString(ticketStatusRequest.getChosenDate());

        Optional<Ticket> ticketOptional = ticketRepository.findByUsernameAndShowtimeAndChosenDate(
                ticketStatusRequest.getUsername(), ticketStatusRequest.getShowtime(), chosenDate);

        if (ticketOptional.isPresent()) {
            logger.info("Ticket found");

            Ticket ticket = ticketOptional.get();
            ticket.setStatus(ticketStatusRequest.getStatus());
            ticketRepository.save(ticket);
            return true;
        }

        logger.info("Ticket not found");

        return false;
    }

    @Override
    public Ticket createTicket(PaymentRequest paymentRequest) {
        logger.info("Creating ticket");

        Ticket ticket = new Ticket();
        Date chosenDate = DateUtils.transformDateFromString(paymentRequest.getChosenDate());

        ticket.setShowtimeId(paymentRequest.getShowtime().getShowtimeId());
        ticket.setStatus(TicketConstants.ACTIVE);
        ticket.setChosenDate(chosenDate);
        ticket.setMovieId(paymentRequest.getMovie().getMovieId());
        ticket.setUsername(paymentRequest.getUsername());
        return ticketRepository.save(ticket);
    }

    @Override
    public PaymentResponse processIosPayment(PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = new PaymentResponse();

        // Save the ticket
        createTicket(paymentRequest);
        logger.info("Ticket created for user {} showtime {} and chosen date {}", paymentRequest.getUsername(),
                paymentRequest.getShowtime(), paymentRequest.getChosenDate());

        paymentResponse.setConfirmed(true);

        return paymentResponse;
    }
}
