package com.vtheatre.service;

import com.stripe.model.Charge;
import com.vtheatre.data.entity.Ticket;
import com.vtheatre.data.model.PaymentRequest;
import com.vtheatre.data.model.TicketStatusRequest;
import com.vtheatre.data.model.VerifyConfCodeRequest;
import com.vtheatre.data.model.VerifyConfCodeResponse;

import org.springframework.stereotype.Service;

@Service
public interface TicketService {

    Ticket createTicket(String confirmationCode, Charge charge, PaymentRequest paymentRequest);

    VerifyConfCodeResponse verifyConfirmationCode(VerifyConfCodeRequest verifyConfCodeRequest);

    boolean updateTicketStatus(TicketStatusRequest ticketStatusRequest);

}
