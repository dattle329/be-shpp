package com.own.moviebooking2.repository;

import com.own.moviebooking2.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
