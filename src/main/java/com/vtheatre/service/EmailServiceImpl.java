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

    @Value("${cloudfront.icon.url}")
    private String iconImg;

    public boolean sendConfirmationCode(PaymentRequest paymentRequest, String confirmationCode) {
        logger.info("Getting ready to send email");
        boolean result = false;
        StringBuilder body = new StringBuilder();

        try {

            body.append("<html>");
            body.append("<body>");
            body.append("<div style='" + "text-align: center'" + ">");
            body.append("<p><img src='" + iconImg + "' alt='" + paymentRequest.getMovie().getTitle() + "' width='"
                    + "157" + "' height='" + "157" + "'/></p>");
            body.append("<p>ENJOY YOUR MOVIE!</p>");
            body.append("<p>Ticket Confirmation Code: " + confirmationCode + "</p>");
            body.append("<p>" + paymentRequest.getMovie().getTitle() + "<br>" + paymentRequest.getMovie().getLength()
                    + "<br>" + paymentRequest.getEmailFormattedDate() + "<br>"
                    + paymentRequest.getShowtime().getShowtime() + "</p>");
            body.append("</div>");
            body.append("<body>");
            body.append("</html>");
            emailConfig.sendMailWithPlainHtmlBody(fromEmail, paymentRequest.getEmailAddress(),
                    EmailConstants.PURCHASE_CONFIRMATION_SUBJ, body.toString());
            result = true;
            logger.info("Successfully sent email");
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("Error sending email to {} with error {}", paymentRequest.getEmailAddress(), e.getMessage());
        }
        return result;
    }
}
