package com.vtheatre.service;

import java.util.List;

import com.vtheatre.data.entity.Movie;
import com.vtheatre.repository.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

	@Override
	public List<Movie> lookup() {
		return movieRepository.findAll();
	}
    
}
