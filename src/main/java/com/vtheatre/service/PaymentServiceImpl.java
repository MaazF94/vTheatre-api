package com.vtheatre.service;

import javax.annotation.PostConstruct;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import com.vtheatre.data.model.PaymentRequest;
import com.vtheatre.data.model.PaymentResponse;
import com.vtheatre.util.TicketUtils;

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
        StringBuilder confirmationCode = new StringBuilder("");

        ChargeCreateParams params = ChargeCreateParams.builder()
                .setAmount(paymentRequest.getMovie().getTicketPrice() * 100L).setCurrency(paymentRequest.getCurrency())
                .setDescription(paymentRequest.getDescription()).setSource(paymentRequest.getTokenId()).build();

        try {

            Charge charge = Charge.create(params);
            logger.info("Successful charge with Id {}", charge.getId());

            if (charge.getCaptured()) {
                // Generate a ticket confirmation code
                confirmationCode = TicketUtils.confirmationCodeGenerator(charge.getId());
                logger.info("Confirmation code created {}", confirmationCode.toString().toUpperCase());

                // Save the ticket
                ticketService.createTicket(confirmationCode.toString(), charge, paymentRequest);
                logger.info("Ticket created");

            }
        } catch (StripeException e) {
            logger.info("Error {} with stripe charge to {} for {} on {}", e.getMessage(),
                    paymentRequest.getMovie().getTitle(),
                    paymentRequest.getChosenDate() + " " + paymentRequest.getShowtime().getShowtime());
        }

        paymentResponse.setConfirmationCode(confirmationCode.toString().toUpperCase());

        return paymentResponse;
    }
}
