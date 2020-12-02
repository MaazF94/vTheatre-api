package com.vtheatre.service;

import java.util.List;

import com.vtheatre.data.entity.MovieInfo;
import com.vtheatre.repository.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class movieServiceImpl implements movieService {

    @Autowired
    private MovieRepository movieRepository;

	@Override
	public List<MovieInfo> lookup() {
		return movieRepository.findAll();
	}
    
}
