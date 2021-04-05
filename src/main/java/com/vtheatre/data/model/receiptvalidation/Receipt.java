package com.vtheatre.data.model.receiptvalidation;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Receipt {

    @JsonProperty("in_app")
    private ArrayList<InApp> inApp;

    public ArrayList<InApp> getInApp() {
        return inApp;
    }

    public void setInApp(ArrayList<InApp> inApp) {
        this.inApp = inApp;
    }

}
