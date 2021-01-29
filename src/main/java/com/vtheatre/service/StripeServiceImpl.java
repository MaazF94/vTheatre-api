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
public class StripeServiceImpl implements StripeService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${stripe.apiKey}")
    private String apiKey;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EmailService emailService;

    @PostConstruct
    public void init() {
        Stripe.apiKey = this.apiKey;
    }

    @Override
    public PaymentResponse charge(PaymentRequest paymentRequest) {
        logger.info("Inside StripeService with {}", paymentRequest);

        PaymentResponse paymentResponse = new PaymentResponse();
        boolean isChargeSuccesful = false;
        boolean isEmailSuccessful = false;
        String confirmationCode = "";

        ChargeCreateParams params = ChargeCreateParams.builder().setAmount(paymentRequest.getMovie().getTicketPrice() * 100L)
                .setCurrency(paymentRequest.getCurrency()).setDescription(paymentRequest.getDescription())
                .setSource(paymentRequest.getTokenId()).setReceiptEmail(paymentRequest.getEmailAddress()).build();

        try {

            Charge charge = Charge.create(params);
            logger.info("Successful charge with {}", charge);

            if (charge.getCaptured()) {
                // Able to capture the charge
                isChargeSuccesful = true;

                // Generate a ticket confirmation code
                confirmationCode = TicketUtils.confirmationCodeGenerator(charge.getId());

                // Save the ticket
                ticketService.createTicket(confirmationCode, charge, paymentRequest);

                // Email the user with showtime and confirmation details
                isEmailSuccessful = emailService.sendConfirmationCode(paymentRequest, confirmationCode);
            }
        } catch (StripeException e) {
            e.printStackTrace();
            logger.info("Error with stripe charge to {} for {}", paymentRequest.getEmailAddress(), paymentRequest.getChosenMovieDate() + " " + paymentRequest.getShowtime().getShowtime());
        }

        paymentResponse.setChargeSuccesful(isChargeSuccesful);
        paymentResponse.setEmailSuccessful(isEmailSuccessful);
        paymentResponse.setEmailAddress(paymentRequest.getEmailAddress());
        paymentResponse.setConfirmationCode(confirmationCode);

        return paymentResponse;
    }

}
