package com.vtheatre.service;

import java.util.List;

import com.vtheatre.data.entity.Movie;
import com.vtheatre.data.entity.VideoTimeWatched;
import com.vtheatre.data.model.VideoTimeWatchedRequest;
import com.vtheatre.data.model.TokenLicenseRequest;

import org.springframework.stereotype.Service;

@Service
public interface MovieService {

    List<Movie> lookup();

    VideoTimeWatched recordTimeVideoWatched(VideoTimeWatchedRequest videoTimeWatched);

    Movie getMovieByMovieId(Long id);

    String getTokenLicense(TokenLicenseRequest tokenLicenseRequest);

}
