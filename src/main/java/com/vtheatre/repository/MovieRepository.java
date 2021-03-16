package com.vtheatre.repository;

import java.util.List;

import com.vtheatre.data.entity.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findAllByOrderByTitleAsc();

}
