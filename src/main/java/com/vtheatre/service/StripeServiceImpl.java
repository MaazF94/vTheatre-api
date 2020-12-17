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

    @PostConstruct
    public void init() {
        Stripe.apiKey = this.apiKey;
    }

    @Override
    public Ticket Charge(PaymentRequest paymentRequest) {
        Ticket ticket = null;
        Charge charge = null;

        ChargeCreateParams params = ChargeCreateParams.builder().setAmount(paymentRequest.getAmount() * 100L)
                .setCurrency(paymentRequest.getCurrency()).setDescription(paymentRequest.getDescription())
                .setSource(paymentRequest.getTokenId()).setReceiptEmail(paymentRequest.getEmailAddress()).build();

        try {
            charge = Charge.create(params);
            // generate a ticket confirmation code
            if (charge.getCaptured()) {
                String confirmationCode = TicketUtils.confirmationCodeGenerator();
                ticket = ticketService.createTicket(confirmationCode, charge, paymentRequest.getShowtime());
            }
        } catch (StripeException e) {
            e.printStackTrace();
        }

        return ticket;
    }



}
