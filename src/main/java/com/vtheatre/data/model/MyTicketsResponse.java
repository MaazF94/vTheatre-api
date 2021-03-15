package com.vtheatre.data.model;

import java.util.Date;

public class MyTicketsResponse {

    private String title;
    private Date chosenDate;
    private String showtime;

    public MyTicketsResponse(String title, String showtime, Date chosenDate) {
        this.title = title;
        this.showtime = showtime;
        this.chosenDate = chosenDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getChosenDate() {
        return chosenDate;
    }

    public void setChosenDate(Date chosenDate) {
        this.chosenDate = chosenDate;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

}
