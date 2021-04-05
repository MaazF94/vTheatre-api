package com.vtheatre.data.model.receiptvalidation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InApp {

    @JsonProperty("cancellation_date")
    private String cancellationDate;

    @JsonProperty("original_transaction_id")
    private String originalTransactionId;

    public String getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(String cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public String getOriginalTransactionId() {
        return originalTransactionId;
    }

    public void setOriginalTransactionId(String originalTransactionId) {
        this.originalTransactionId = originalTransactionId;
    }

}
