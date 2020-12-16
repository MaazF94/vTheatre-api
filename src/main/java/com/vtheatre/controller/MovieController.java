package com.vtheatre.controller;

import java.util.List;

import com.vtheatre.data.entity.Movie;
import com.vtheatre.service.MovieService;

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
    MovieService movieService;

    @GetMapping(path = "/getMovies")
    public ResponseEntity<List<Movie>> getMovies() {
        List<Movie> movies = movieService.lookup();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
}