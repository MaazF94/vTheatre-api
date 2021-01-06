package com.vtheatre.service;

import javax.annotation.PostConstruct;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import com.vtheatre.data.entity.Ticket;
import com.vtheatre.data.model.PaymentRequest;
import com.vtheatre.util.TicketUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripeServiceImpl implements StripeService {

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
    public Ticket Charge(PaymentRequest paymentRequest) {
        Ticket ticket = null;

        ChargeCreateParams params = ChargeCreateParams.builder().setAmount(paymentRequest.getAmount() * 100L)
                .setCurrency(paymentRequest.getCurrency()).setDescription(paymentRequest.getDescription())
                .setSource(paymentRequest.getTokenId()).setReceiptEmail(paymentRequest.getEmailAddress()).build();

        try {
            Charge charge = Charge.create(params);
            if (charge.getCaptured()) {
                // Generate a ticket confirmation code
                String confirmationCode = TicketUtils.confirmationCodeGenerator(charge.getId());
                // Save the ticket
                ticket = ticketService.createTicket(confirmationCode, charge, paymentRequest.getShowtime());
                // Email the user with showtime and confirmation details
                emailService.sendConfirmationCode(paymentRequest.getEmailAddress(), confirmationCode,
                        paymentRequest.getMovie(), paymentRequest.getShowtime().getShowtime(),
                        paymentRequest.getChosenMovieDate());
            }
        } catch (StripeException e) {
            e.printStackTrace();
        }

        return ticket;
    }

}
