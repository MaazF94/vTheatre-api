package com.vtheatre.data.model;

public class VerifyTicketResponse {

    private String status;
    private boolean exists;
    private String appleTransactionReceipt;
    private String appleTransactionId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public String getAppleTransactionReceipt() {
        return appleTransactionReceipt;
    }

    public void setAppleTransactionReceipt(String appleTransactionReceipt) {
        this.appleTransactionReceipt = appleTransactionReceipt;
    }

    public String getAppleTransactionId() {
        return appleTransactionId;
    }

    public void setAppleTransactionId(String appleTransactionId) {
        this.appleTransactionId = appleTransactionId;
    }

}
