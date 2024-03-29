package com.vtheatre.data.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Movie {

    @Id
    @GeneratedValue
    private Long movieId;
    private String title;
    private String rating;
    private String length;
    private String genre;
    private Date startDate;
    private Date endDate;
    private String img;
    @OneToMany(mappedBy = "movieId")
    @OrderBy("str_to_date(showtime,'%l:%i %p')")
    private List<Showtime> showtimes;
    private String synopsis;
    private String cast;
    private Long ticketPrice;
    private String vid;
    private String iosProductId;
    private int active;
    private int drmEnabled;
    private String fairplayPlayback;
    private String widevinePlayback;

    public String getFairplayPlayback() {
        return fairplayPlayback;
    }

    public void setFairplayPlayback(String fairplayPlayback) {
        this.fairplayPlayback = fairplayPlayback;
    }

    public String getWidevinePlayback() {
        return widevinePlayback;
    }

    public void setWidevinePlayback(String widevinePlayback) {
        this.widevinePlayback = widevinePlayback;
    }

    public int getDrmEnabled() {
        return drmEnabled;
    }

    public void setDrmEnabled(int drmEnabled) {
        this.drmEnabled = drmEnabled;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public Long getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Long ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getIosProductId() {
        return iosProductId;
    }

    public void setIosProductId(String iosProductId) {
        this.iosProductId = iosProductId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
