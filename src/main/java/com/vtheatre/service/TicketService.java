package com.vtheatre.service;

import java.util.List;

import com.stripe.model.Charge;
import com.vtheatre.data.entity.Ticket;
import com.vtheatre.data.model.MyTicketsResponse;
import com.vtheatre.data.model.PaymentRequest;
import com.vtheatre.data.model.PaymentResponse;
import com.vtheatre.data.model.TicketStatusRequest;
import com.vtheatre.data.model.VerifyTicketRequest;
import com.vtheatre.data.model.VerifyTicketResponse;

import org.springframework.stereotype.Service;

@Service
public interface TicketService {

    Ticket createTicket(Charge charge, PaymentRequest paymentRequest);

    Ticket createTicket(PaymentRequest paymentRequest);

    VerifyTicketResponse verifyTicket(VerifyTicketRequest verifyConfCodeRequest);

    boolean updateTicketStatus(TicketStatusRequest ticketStatusRequest);

    PaymentResponse processIosPayment(PaymentRequest paymentRequest);

    List<MyTicketsResponse> getTickets(String string);

}
