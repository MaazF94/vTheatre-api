package com.vtheatre.controller;

import java.util.List;

import com.vtheatre.data.entity.Movie;
import com.vtheatre.data.entity.VideoTimeWatched;
import com.vtheatre.data.model.VideoTimeWatchedRequest;
import com.vtheatre.service.MovieService;
import com.vtheatre.util.CloudFrontUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vtheatre/v1")
public class MovieController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MovieService movieService;

    @Autowired
    CloudFrontUtils cloudFoundryUtils;

    @GetMapping(path = "/getMovies")
    public ResponseEntity<List<Movie>> getMovies() {
        logger.info("Before retrieving movies");
        List<Movie> movies = movieService.lookup();
        logger.info("After retrieving movies");
        for (Movie movie : movies) {
            movie.setImg(cloudFoundryUtils.generateSignedUrl(movie.getImg()));
            // Too early in UI to set vid url
        }

        logger.info("Img URLs set");

        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @PostMapping(path = "/recordVideoTimeWatched")
    public ResponseEntity<VideoTimeWatched> recordTimeVideoWatched(
            @RequestBody VideoTimeWatchedRequest videoTimeWatchedRequest) {
        logger.info("Recording time watched for movie Id {} with hours {}, minutes {}, seconds {}",
                videoTimeWatchedRequest.getMovieId(), videoTimeWatchedRequest.getHours(),
                videoTimeWatchedRequest.getMinutes(), videoTimeWatchedRequest.getSeconds());

        VideoTimeWatched result = movieService.recordTimeVideoWatched(videoTimeWatchedRequest);
        logger.info("Saved the recorded time watched");
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(path = "/refreshMovieFiles")
    public ResponseEntity<Movie> getFiles(@RequestBody Movie movie) {
        logger.info("refreshing files for {}", movie.getTitle());

        // Refresh the S3 file keys for the movie
        movie = movieService.getMovieByMovieId(movie.getMovieId());
        logger.info("refreshed S3 file keys from DB");

        movie.setImg(cloudFoundryUtils.generateSignedUrl(movie.getImg()));
        movie.setVid(cloudFoundryUtils.generateSignedUrl(movie.getVid()));

        logger.info("files refreshed");
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }
}