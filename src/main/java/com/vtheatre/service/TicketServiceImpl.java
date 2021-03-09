package com.vtheatre.service;

import java.util.Date;
import java.util.Optional;

import com.stripe.model.Charge;
import com.vtheatre.common.TicketConstants;
import com.vtheatre.data.entity.Ticket;
import com.vtheatre.data.model.PaymentRequest;
import com.vtheatre.data.model.PaymentResponse;
import com.vtheatre.data.model.TicketStatusRequest;
import com.vtheatre.data.model.VerifyConfCodeRequest;
import com.vtheatre.data.model.VerifyConfCodeResponse;
import com.vtheatre.repository.TicketRepository;
import com.vtheatre.util.DateUtils;
import com.vtheatre.util.TicketUtils;

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
    public Ticket createTicket(String confirmationCode, Charge charge, PaymentRequest paymentRequest) {
        logger.info("Creating ticket");

        Ticket ticket = new Ticket();
        Date chosenDate = DateUtils.transformDateFromString(paymentRequest.getChosenDate());

        ticket.setChargeId(charge.getId());
        ticket.setConfirmationCode(confirmationCode);
        ticket.setShowtimeId(paymentRequest.getShowtime().getShowtimeId());
        ticket.setStatus(TicketConstants.ACTIVE);
        ticket.setChosenDate(chosenDate);
        ticket.setMovieId(paymentRequest.getMovie().getMovieId());
        return ticketRepository.save(ticket);
    }

    @Override
    public VerifyConfCodeResponse verifyConfirmationCode(VerifyConfCodeRequest verifyConfCodeRequest) {
        VerifyConfCodeResponse ticketResponse = new VerifyConfCodeResponse();
        Date chosenDate = DateUtils.transformDateFromString(verifyConfCodeRequest.getChosenDate());

        Optional<Ticket> ticket = ticketRepository.findByConfirmationCodeAndShowtimeAndChosenDate(
                verifyConfCodeRequest.getConfirmationCode(), verifyConfCodeRequest.getShowtime(), chosenDate);

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
        logger.info("Finding ticket with code {}", ticketStatusRequest.getConfirmationCode());

        Optional<Ticket> ticketOptional = ticketRepository
                .findByConfirmationCode(ticketStatusRequest.getConfirmationCode());

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
    public Ticket createTicket(String confirmationCode, PaymentRequest paymentRequest) {
        logger.info("Creating ticket");

        Ticket ticket = new Ticket();
        Date chosenDate = DateUtils.transformDateFromString(paymentRequest.getChosenDate());

        ticket.setConfirmationCode(confirmationCode);
        ticket.setShowtimeId(paymentRequest.getShowtime().getShowtimeId());
        ticket.setStatus(TicketConstants.ACTIVE);
        ticket.setChosenDate(chosenDate);
        ticket.setMovieId(paymentRequest.getMovie().getMovieId());
        return ticketRepository.save(ticket);
    }

    @Override
    public PaymentResponse processIosPayment(PaymentRequest paymentRequest) {
        StringBuilder confirmationCode = new StringBuilder("");
        PaymentResponse paymentResponse = new PaymentResponse();

        // Generate a ticket confirmation code
        confirmationCode = TicketUtils.confirmationCodeGenerator();
        // Save the ticket
        createTicket(confirmationCode.toString(), paymentRequest);
        logger.info("Confirmation code created {}", confirmationCode.toString().toUpperCase());

        paymentResponse.setConfirmationCode(confirmationCode.toString().toUpperCase());

        return paymentResponse;
    }
}
