package com.vtheatre.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VideoTimeWatched {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long videoTimeWatchedId;
    private Long hours;
    private Long minutes;
    private Long seconds;
    private Long movieId;

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Long getMinutes() {
        return minutes;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    public Long getSeconds() {
        return seconds;
    }

    public void setSeconds(Long seconds) {
        this.seconds = seconds;
    }

    public Long getVideoTimeWatchedId() {
        return videoTimeWatchedId;
    }

    public void setVideoTimeWatchedId(Long videoTimeWatchedId) {
        this.videoTimeWatchedId = videoTimeWatchedId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

}
