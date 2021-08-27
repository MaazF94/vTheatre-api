package com.vtheatre.service;

import java.util.List;
import java.util.Optional;

import com.vtheatre.data.entity.Movie;
import com.vtheatre.data.entity.VideoTimeWatched;
import com.vtheatre.data.model.TokenLicenseRequest;
import com.vtheatre.data.model.VideoTimeWatchedRequest;
import com.vtheatre.repository.MovieRepository;
import com.vtheatre.repository.VideoTimeWatchedRepository;
import com.vtheatre.data.model.drm.token.*;
import com.vtheatre.exception.drm.PallyConTokenException;
import com.vtheatre.config.drm.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MovieServiceImpl implements MovieService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private VideoTimeWatchedRepository videoTimeWatchedRepository;

	@Value("${drm.pallycon.sitekey}")
	private String siteKey;

	@Value("${drm.pallycon.accesskey}")
	private String accessKey;

	@Value("${drm.pallycon.siteid}")
	private String siteId;

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

	@Override
	public String getTokenLicense(TokenLicenseRequest tokenLicenseRequest) {
		String licenseToken = "";
		PallyConDrmTokenPolicy policy = null;
		PallyConDrmTokenClient token = null;

		try {
			policy = new PallyConDrmTokenPolicy.PolicyBuilder().build();

			if (tokenLicenseRequest.getSourceType().equals("ios")) {
				token = new PallyConDrmTokenClient().fairplay().siteId(siteId).siteKey(siteKey).accessKey(accessKey)
						.userId(tokenLicenseRequest.getUserId()).cId(tokenLicenseRequest.getContentId()).policy(policy)
						.responseFormat(ResponseFormat.ORIGINAL);
				licenseToken = token.execute();
			} else if (tokenLicenseRequest.getSourceType().equals("android")) {
				token = new PallyConDrmTokenClient().widevine().siteId(siteId).siteKey(siteKey).accessKey(accessKey)
						.userId(tokenLicenseRequest.getUserId()).cId(tokenLicenseRequest.getContentId()).policy(policy)
						.responseFormat(ResponseFormat.ORIGINAL);
				licenseToken = token.execute();
			}

		} catch (PallyConTokenException e) {
			logger.error("Error creating license token {}", e.getMessage());
		} catch (Exception e) {
			logger.error("Unexpected error creating license token {}", e.getMessage());
		}
		return licenseToken;
	}
}
