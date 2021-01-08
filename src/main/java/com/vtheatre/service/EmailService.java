package com.vtheatre.service;

import com.vtheatre.data.model.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    boolean sendConfirmationCode(PaymentRequest paymentRequest, String confirmationCode);


    
}
