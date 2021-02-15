package com.vtheatre.data.model;

import com.vtheatre.data.entity.Movie;
import com.vtheatre.data.entity.Showtime;

public class PaymentRequest {

    private String tokenId;
    private String description;
    private String currency;
    private String emailAddress;
    private Showtime showtime;
    private Movie movie;
    private String emailFormattedDate;
    private String ticketFormattedDate;

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

    public String getEmailFormattedDate() {
        return emailFormattedDate;
    }

    public void setEmailFormattedDate(String emailFormattedDate) {
        this.emailFormattedDate = emailFormattedDate;
    }

    public String getTicketFormattedDate() {
        return ticketFormattedDate;
    }

    public void setTicketFormattedDate(String ticketFormattedDate) {
        this.ticketFormattedDate = ticketFormattedDate;
    }

}
