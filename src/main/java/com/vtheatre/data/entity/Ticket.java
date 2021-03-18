package com.vtheatre.data.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;
    private Long showtimeId;
    private String chargeId;
    private String status;
    private Long movieId;
    private Date chosenDate;
    private String username;
    private String appleOrderId;
    private String appleTransactionReceipt;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Long showtimeId) {
        this.showtimeId = showtimeId;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Date getChosenDate() {
        return chosenDate;
    }

    public void setChosenDate(Date chosenDate) {
        this.chosenDate = chosenDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAppleOrderId() {
        return appleOrderId;
    }

    public void setAppleOrderId(String appleOrderId) {
        this.appleOrderId = appleOrderId;
    }

    public String getAppleTransactionReceipt() {
        return appleTransactionReceipt;
    }

    public void setAppleTransactionReceipt(String appleTransactionReceipt) {
        this.appleTransactionReceipt = appleTransactionReceipt;
    }
}
