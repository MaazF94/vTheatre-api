package com.vtheatre.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.stripe.model.Charge;
import com.vtheatre.common.TicketConstants;
import com.vtheatre.data.entity.Ticket;
import com.vtheatre.data.model.MyTicketsResponse;
import com.vtheatre.data.model.PaymentRequest;
import com.vtheatre.data.model.PaymentResponse;
import com.vtheatre.data.model.TicketStatusRequest;
import com.vtheatre.data.model.VerifyTicketRequest;
import com.vtheatre.data.model.VerifyTicketResponse;
import com.vtheatre.data.model.receiptvalidation.InApp;
import com.vtheatre.data.model.receiptvalidation.RequestBody;
import com.vtheatre.data.model.receiptvalidation.ResponseBody;
import com.vtheatre.data.model.receiptvalidation.ValidateReceiptRequest;
import com.vtheatre.repository.TicketRepository;
import com.vtheatre.util.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TicketServiceImpl implements TicketService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TicketRepository ticketRepository;

    @Value("${apple.receipt.validation.url}")
    private String validateReceiptUrl;

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

        Optional<Ticket> ticket = ticketRepository.findByUsernameAndShowtimeAndChosenDateAndMovieId(
                verifyConfCodeRequest.getUsername(), verifyConfCodeRequest.getShowtime(), chosenDate,
                verifyConfCodeRequest.getMovieId());

        if (ticket.isPresent()) {
            ticketResponse.setStatus(ticket.get().getStatus());
            ticketResponse.setExists(true);
            ticketResponse.setAppleTransactionReceipt(ticket.get().getAppleTransactionReceipt());
            ticketResponse.setAppleTransactionId(ticket.get().getAppleTransactionId());
        } else {
            ticketResponse.setExists(false);
        }

        return ticketResponse;
    }

    @Override
    public boolean updateTicketStatus(TicketStatusRequest ticketStatusRequest) {
        logger.info("Finding ticket for user {}", ticketStatusRequest.getUsername());

        Date chosenDate = DateUtils.transformDateFromString(ticketStatusRequest.getChosenDate());

        Optional<Ticket> ticketOptional = ticketRepository.findByUsernameAndShowtimeAndChosenDateAndMovieId(
                ticketStatusRequest.getUsername(), ticketStatusRequest.getShowtime(), chosenDate,
                ticketStatusRequest.getMovieId());

        if (ticketOptional.isPresent()) {
            logger.info("Ticket found");

            Ticket ticket = ticketOptional.get();
            ticket.setStatus(ticketStatusRequest.getStatus());
            ticketRepository.save(ticket);
            return true;
        } else {
            logger.info("Ticket not found");
        }

        return false;
    }

    private Ticket createTicket(PaymentRequest paymentRequest) {
        logger.info("Creating ticket");

        Ticket ticket = new Ticket();
        Date chosenDate = DateUtils.transformDateFromString(paymentRequest.getChosenDate());

        ticket.setShowtimeId(paymentRequest.getShowtime().getShowtimeId());
        ticket.setStatus(TicketConstants.ACTIVE);
        ticket.setChosenDate(chosenDate);
        ticket.setMovieId(paymentRequest.getMovie().getMovieId());
        ticket.setUsername(paymentRequest.getUsername());
        if (paymentRequest.getAppleTransactionId() != null && paymentRequest.getAppleTransactionReceipt() != null) {
            ticket.setAppleTransactionId(paymentRequest.getAppleTransactionId());
            ticket.setAppleTransactionReceipt(paymentRequest.getAppleTransactionReceipt());
        }
        return ticketRepository.save(ticket);
    }

    @Override
    public PaymentResponse completeIosPayment(PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = new PaymentResponse();

        try {
            // Save the ticket
            createTicket(paymentRequest);

            logger.info("Ticket created for user {} showtime {} and chosen date {}", paymentRequest.getUsername(),
                    paymentRequest.getShowtime(), paymentRequest.getChosenDate());

            paymentResponse.setConfirmed(true);
        } catch (Exception e) {
            paymentResponse.setConfirmed(false);
            logger.info("Duplicate ticket found for user {} showtime {} and chosen date {}",
                    paymentRequest.getUsername(), paymentRequest.getShowtime(), paymentRequest.getChosenDate());
        }

        return paymentResponse;
    }

    // delete this after UI is live in next update
    @Override
    public PaymentResponse processIosPayment(PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = new PaymentResponse();

        try {
            // Save the ticket
            createTicket(paymentRequest);

            logger.info("Ticket created for user {} showtime {} and chosen date {}", paymentRequest.getUsername(),
                    paymentRequest.getShowtime(), paymentRequest.getChosenDate());

            paymentResponse.setConfirmed(true);
        } catch (Exception e) {
            paymentResponse.setConfirmed(false);
            logger.info("Duplicate ticket found for user {} showtime {} and chosen date {}",
                    paymentRequest.getUsername(), paymentRequest.getShowtime(), paymentRequest.getChosenDate());
        }

        return paymentResponse;
    }

    @Override
    public List<MyTicketsResponse> getTickets(String username) {
        return ticketRepository.findByUsername(username);
    }

    @Override
    public boolean validateReceipt(ValidateReceiptRequest validateReceiptRequest) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            URI uri = new URI(validateReceiptUrl);
            RequestBody requestBody = new RequestBody(validateReceiptRequest.getTransactionReceipt());
            ResponseEntity<ResponseBody> result = restTemplate.postForEntity(uri, requestBody, ResponseBody.class);
            ResponseBody responseBody = result.getBody();
            if (responseBody.getStatus().equals("21007")) {
                uri = new URI("https://sandbox.itunes.apple.com/verifyReceipt");
                result = restTemplate.postForEntity(uri, requestBody, ResponseBody.class);
                responseBody = result.getBody();
            } else if (responseBody.getStatus().equals("21008")) {
                uri = new URI("https://buy.itunes.apple.com/verifyReceipt");
                result = restTemplate.postForEntity(uri, requestBody, ResponseBody.class);
                responseBody = result.getBody();
            }
            for (InApp transaction : responseBody.getReceipt().getInApp()) {
                if (transaction.getCancellationDate() != null
                        && transaction.getOriginalTransactionId().equals(validateReceiptRequest.getTransactionId())) {
                    setTicketStatusRefunded(transaction.getOriginalTransactionId());
                    return false;
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void setTicketStatusRefunded(String transactionId) {
        logger.info("Finding ticket for transactionId {}", transactionId);
        ticketRepository.setTicketByAppleTransactionId(transactionId);
        logger.info("Set ticket status to refunded");
    }

}
