package com.vtheatre.data.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MovieInfo {

    @Id
    @GeneratedValue
    private Integer movieInfoId;
    private String title;
    private String rating;
    private String length;
    private String genre;
    private Date startDate;
    private Date endDate;
    private String img;
    @OneToMany(mappedBy = "movieInfoId")
    private List<Showtime> showtime;

    public Integer getId() {
        return movieInfoId;
    }

    public void setId(Integer id) {
        this.movieInfoId = id;
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

    public List<Showtime> getShowtime() {
        return showtime;
    }

    public void setShowtime(List<Showtime> showtime) {
        this.showtime = showtime;
    }
}
