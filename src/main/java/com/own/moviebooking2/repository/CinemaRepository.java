package com.own.moviebooking2.repository;

import com.own.moviebooking2.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
