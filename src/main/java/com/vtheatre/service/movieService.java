package com.vtheatre.service;

import java.util.List;

import com.vtheatre.data.entity.Movie;

import org.springframework.stereotype.Service;

@Service
public interface MovieService {

    List<Movie> lookup();

}
