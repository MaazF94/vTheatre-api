package com.vtheatre.data.model;

import com.vtheatre.data.entity.Movie;
import com.vtheatre.data.entity.Showtime;

public class PaymentRequest {

    private String tokenId;
    private String description;
    private String currency;
    private Showtime showtime;
    private Movie movie;
    private String chosenDate;
    private String username;
    private String appleTransactionId;
    private String appleTransactionReceipt;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getChosenDate() {
        return chosenDate;
    }

    public void setChosenDate(String chosenDate) {
        this.chosenDate = chosenDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAppleTransactionId() {
        return appleTransactionId;
    }

    public void setAppleTransactionId(String appleTransactionId) {
        this.appleTransactionId = appleTransactionId;
    }

    public String getAppleTransactionReceipt() {
        return appleTransactionReceipt;
    }

    public void setAppleTransactionReceipt(String appleTransactionReceipt) {
        this.appleTransactionReceipt = appleTransactionReceipt;
    }

}
