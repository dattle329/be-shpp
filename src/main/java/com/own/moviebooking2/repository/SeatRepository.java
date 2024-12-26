package com.own.moviebooking2.repository;

import com.own.moviebooking2.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
