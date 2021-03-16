package com.vtheatre.data.model;

public class VerifyTicketRequest {

    private String username;
    private String chosenDate;
    private String showtime;
    private Long movieId;

    public VerifyTicketRequest() {

    }

    public VerifyTicketRequest(String username, String chosenDate, String showtime, Long movieId) {
        this.username = username;
        this.chosenDate = chosenDate;
        this.showtime = showtime;
        this.movieId = movieId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChosenDate() {
        return chosenDate;
    }

    public void setChosenDate(String chosenDate) {
        this.chosenDate = chosenDate;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
