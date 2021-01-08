package com.vtheatre.service;

import javax.mail.MessagingException;
import com.vtheatre.common.EmailConfig;
import com.vtheatre.common.EmailConstants;
import com.vtheatre.data.model.PaymentRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmailConfig emailConfig;

    @Value("${mail.from}")
    private String fromEmail;

    public boolean sendConfirmationCode(PaymentRequest paymentRequest, String confirmationCode) {
        logger.info("Inside EmailService with request {} and confirmation code {}", paymentRequest, confirmationCode);

        StringBuilder body = new StringBuilder();
        boolean result = false;

        body.append("<html>");
        body.append("<body>");
        body.append("<div style='" + "text-align: center'" + ">");
        body.append("<p>Ticket Confirmation Code: " + confirmationCode + "</p>");
        body.append("<p>ENJOY YOUR MOVIE!</p>");
        body.append("<p><img src='" + paymentRequest.getMovie().getImg() + "' alt='"
                + paymentRequest.getMovie().getTitle() + "' width='" + "157" + "' height='" + "232" + "'/></p>");
        body.append("<p>Black Widow<br>" + paymentRequest.getMovie().getLength() + "<br>"
                + paymentRequest.getChosenMovieDate() + "<br>" + paymentRequest.getShowtime().getShowtime() + "</p>");
        body.append("</div>");
        body.append("<body>");
        body.append("</html>");

        try {
            emailConfig.sendMailWithPlainHtmlBody(fromEmail, paymentRequest.getEmailAddress(),
                    EmailConstants.PURCHASE_CONFIRMATION_SUBJ, body.toString());
            result = true;
            logger.info("Successfully sent email with result {}", result);
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("Error sending email with result {}", result, e);
        }
        return result;
    }
}
