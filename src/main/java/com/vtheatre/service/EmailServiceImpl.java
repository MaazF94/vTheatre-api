package com.vtheatre.service;

import javax.mail.MessagingException;
import com.vtheatre.common.EmailConfig;
import com.vtheatre.data.entity.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailConfig emailConfig;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public boolean sendConfirmationCode(String toEmail, String confirmationCode, Movie movie, String chosenShowtime,
            String chosenMovieDate) {
        StringBuilder body = new StringBuilder();
        boolean result = false;

        body.append("<html>");
        body.append("<body>");
        body.append("<div style='" + "text-align: center'" + ">");
        body.append("<p>Ticket Confirmation Code: " + confirmationCode + "</p>");
        body.append("<p>ENJOY YOUR MOVIE!</p>");
        body.append("<p><img src='" + movie.getImg() + "' alt='" + movie.getTitle() + "' width='" + "157" + "' height='"
                + "232" + "'/></p>");
        body.append("<p>Black Widow<br>2HRS 10MINS<br>Wednesday, Jan 06, 2021<br>12:00 PM</p>");
        body.append("</div>");
        body.append("<body>");
        body.append("</html>");

        try {
            emailConfig.sendMailWithPlainHtmlBody(fromEmail, toEmail, "vTheatre Purchase Confirmation - Please Read",
                    body.toString());
            result = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
