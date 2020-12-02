package com.vtheatre.service;

import java.util.List;

import com.vtheatre.data.entity.MovieInfo;

import org.springframework.stereotype.Service;

@Service
public interface movieService {

    List<MovieInfo> lookup();

}
