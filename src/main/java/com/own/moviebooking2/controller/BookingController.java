package com.own.moviebooking2.controller;

import com.own.moviebooking2.entity.*;
import com.own.moviebooking2.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor

public class BookingController {
    private final BookingService bookingService;

    @PostMapping(value = "/add/cinema")
    public ResponseEntity<List<Cinema>> addCinema(@RequestBody Cinema cinema) {
        return bookingService.addCinema(cinema);
    }

    @PostMapping(value = "/add/room/cinema/{cinemaId}")
    public ResponseEntity<List<Cinema>> addRoom(@RequestBody Room room, @PathVariable Long cinemaId) {
        return bookingService.addRoom(room, cinemaId);
    }

    @PostMapping(value = "add/seat/room/{roomId}")
    public List<Seat> addSeat(@RequestBody List<Seat> seat, @PathVariable Long roomId) {
        return bookingService.addSeat(seat, roomId);
    }

    @PostMapping(value = "add/movie")
    public ResponseEntity<List<Movie>> addMovie(@RequestBody Movie movie) {
        return bookingService.addMovie(movie);
    }

    @PostMapping(value = "add/showtime/{roomId}/movie/{movieId}")
    public List<Showtime> addShowtime(@RequestBody Showtime showtime, @PathVariable("roomId") Long roomId,
                                      @PathVariable("movieId") Long movieId){
        return bookingService.addShowtime(showtime, roomId, movieId);
    }

    @GetMapping(value = "/room/find/all")
    public List<Room> findAll() {
        return bookingService.findAllRooms();
    }
}
