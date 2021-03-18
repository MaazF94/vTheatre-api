package com.vtheatre.service;

import java.util.List;
import java.util.Optional;

import com.vtheatre.data.entity.Movie;
import com.vtheatre.data.entity.VideoTimeWatched;
import com.vtheatre.data.model.VideoTimeWatchedRequest;
import com.vtheatre.repository.MovieRepository;
import com.vtheatre.repository.VideoTimeWatchedRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private VideoTimeWatchedRepository videoTimeWatchedRepository;

	@Override
	public List<Movie> lookup() {
		int active = 1;
		return movieRepository.findByActiveOrderByTitleAsc(active);
	}

	@Override
	public VideoTimeWatched recordTimeVideoWatched(VideoTimeWatchedRequest timeVideoWatchedRequest) {
		VideoTimeWatched videoTimeWatched = new VideoTimeWatched();
		videoTimeWatched.setHours(timeVideoWatchedRequest.getHours());
		videoTimeWatched.setMinutes(timeVideoWatchedRequest.getMinutes());
		videoTimeWatched.setSeconds(timeVideoWatchedRequest.getSeconds());
		videoTimeWatched.setMovieId(timeVideoWatchedRequest.getMovieId());

		return videoTimeWatchedRepository.save(videoTimeWatched);
	}

	@Override
	public Movie getMovieByMovieId(Long id) {
		Optional<Movie> movie = movieRepository.findById(id);
		return movie.get();
	}

}
