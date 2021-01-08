package com.vtheatre.data.model;

public class PaymentResponse {

    private String confirmationCode;
    private String emailAddress;
    private boolean isEmailSuccessful;
    private boolean isChargeSuccesful;

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isEmailSuccessful() {
        return isEmailSuccessful;
    }

    public void setEmailSuccessful(boolean isEmailSuccessful) {
        this.isEmailSuccessful = isEmailSuccessful;
    }

    public boolean isChargeSuccesful() {
        return isChargeSuccesful;
    }

    public void setChargeSuccesful(boolean isChargeSuccesful) {
        this.isChargeSuccesful = isChargeSuccesful;
    }
}
