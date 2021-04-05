package com.vtheatre.data.model.receiptvalidation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestBody {

    @JsonProperty("receipt-data")
    private String receiptData;

    public RequestBody() {
    }

    public RequestBody(String receiptData) {
        this.receiptData = receiptData;
    }

    public String getReceiptData() {
        return receiptData;
    }

    public void setReceiptData(String receiptData) {
        this.receiptData = receiptData;
    }

}
