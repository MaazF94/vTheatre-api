package com.vtheatre.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Showtime {

    @Id
    @GeneratedValue
    private Integer showtimeId;
    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id", nullable = false)
    private Integer movieId;
    private String showtime;

    public Integer getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Integer showtimeId) {
        this.showtimeId = showtimeId;
    }

    public Integer getMovieInfoId() {
        return movieId;
    }

    public void setMovieInfoId(Integer movieInfoId) {
        this.movieId = movieInfoId;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

}
