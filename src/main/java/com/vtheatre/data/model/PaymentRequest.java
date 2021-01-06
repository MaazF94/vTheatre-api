package com.vtheatre.data.model;

import com.vtheatre.data.entity.Movie;
import com.vtheatre.data.entity.Showtime;

public class PaymentRequest {

    private String tokenId;
    private Long amount;
    private String description;
    private String currency;
    private String emailAddress;
    private Showtime showtime;
    private Movie movie;
    private String chosenMovieDate;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    public String getChosenMovieDate() {
        return chosenMovieDate;
    }

    public void setChosenMovieDate(String chosenMovieDate) {
        this.chosenMovieDate = chosenMovieDate;
    }

}
