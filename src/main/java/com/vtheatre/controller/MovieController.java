package com.vtheatre.controller;

import java.util.List;

import com.vtheatre.data.entity.MovieInfo;
import com.vtheatre.service.movieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vtheatre/v1")
public class MovieController {

    @Autowired
    movieService movieService;

    @GetMapping(path = "/getMovies")
    public ResponseEntity<List<MovieInfo>> getMovies() {
        List<MovieInfo> movie = movieService.lookup();
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }
}