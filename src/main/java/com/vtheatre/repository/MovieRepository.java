package com.vtheatre.repository;

import com.vtheatre.data.entity.MovieInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieInfo, Long> {
    
}
