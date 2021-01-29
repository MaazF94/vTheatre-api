package com.vtheatre.repository;

import com.vtheatre.data.entity.VideoTimeWatched;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoTimeWatchedRepository extends JpaRepository<VideoTimeWatched, Long> {

}
