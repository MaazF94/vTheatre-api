package com.vtheatre.service;


import com.vtheatre.data.entity.Movie;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    boolean sendConfirmationCode(String toEmail, String confirmationCode, Movie movie, String string, String string2);


    
}
