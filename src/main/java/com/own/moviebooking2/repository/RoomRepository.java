package com.own.moviebooking2.repository;

import com.own.moviebooking2.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
