package com.vtheatre.service;

import javax.annotation.PostConstruct;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import com.vtheatre.data.model.PaymentRequest;
import com.vtheatre.data.model.PaymentResponse;
import com.vtheatre.data.model.VerifyTicketRequest;
import com.vtheatre.data.model.VerifyTicketResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceImpl implements PaymentService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${stripe.apiKey}")
    private String apiKey;

    @Autowired
    private TicketService ticketService;

    @PostConstruct
    public void init() {
        Stripe.apiKey = this.apiKey;
    }

    @Override
    public PaymentResponse processAndroidPayment(PaymentRequest paymentRequest) {
        logger.info("Before creating charge");

        PaymentResponse paymentResponse = new PaymentResponse();

        ChargeCreateParams params = ChargeCreateParams.builder()
                .setAmount(paymentRequest.getMovie().getTicketPrice() * 100L).setCurrency(paymentRequest.getCurrency())
                .setDescription(paymentRequest.getDescription()).setSource(paymentRequest.getTokenId()).build();

        try {

            // See if the user already has a ticket to this showing
            VerifyTicketResponse verifyTicketResponse = ticketService
                    .verifyTicket(new VerifyTicketRequest(paymentRequest.getUsername(), paymentRequest.getChosenDate(),
                            paymentRequest.getShowtime().getShowtime(), paymentRequest.getMovie().getMovieId()));

            if (!verifyTicketResponse.isExists()) {
                Charge charge = Charge.create(params);
                logger.info("Successful charge with Id {}", charge.getId());

                if (charge.getCaptured()) {

                    // Save the ticket
                    ticketService.createTicket(charge, paymentRequest);
                    paymentResponse.setConfirmed(true);

                    logger.info("Ticket created");

                }
            } else {
                logger.info("Duplicate ticket found for user {} showtime {} and chosen date {}",
                        paymentRequest.getUsername(), paymentRequest.getShowtime(), paymentRequest.getChosenDate());
                paymentResponse.setConfirmed(false);
            }

        } catch (StripeException e) {
            logger.info("Error {} with stripe charge to {} for {} on {}", e.getMessage(),
                    paymentRequest.getMovie().getTitle(),
                    paymentRequest.getChosenDate() + " " + paymentRequest.getShowtime().getShowtime());
        }

        return paymentResponse;
    }
}
