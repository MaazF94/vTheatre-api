package com.vtheatre.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Showtime {

    @Id
    @GeneratedValue
    private Long showtimeId;
    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id", nullable = false)
    private Long movieId;
    private String showtime;

    public Long getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Long showtimeId) {
        this.showtimeId = showtimeId;
    }

    public Long getMovieInfoId() {
        return movieId;
    }

    public void setMovieInfoId(Long movieInfoId) {
        this.movieId = movieInfoId;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

}
