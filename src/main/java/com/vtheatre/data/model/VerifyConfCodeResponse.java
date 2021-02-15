package com.vtheatre.data.model;

public class VerifyConfCodeResponse {

    private String status;
    private boolean exists;

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

}
