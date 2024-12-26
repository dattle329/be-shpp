package com.own.moviebooking2.service;

import com.own.moviebooking2.entity.*;
import com.own.moviebooking2.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {
    private final CinemaRepository cinemaRepository;

    private final RoomRepository roomRepository;

    private final SeatRepository seatRepository;

    private final MovieRepository movieRepository;

    private final ShowtimeRepository showtimeRepository;

    public ResponseEntity<List<Cinema>> addCinema(Cinema cinema) {
        cinemaRepository.save(cinema);
        return new ResponseEntity<>(cinemaRepository.findAll(), HttpStatus.CREATED);
    }

    public ResponseEntity<List<Cinema>> addRoom(Room room, Long cinemaId) {
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseThrow(() -> new RuntimeException("Cinema Not Found"));
        room.setCinema(cinema);
        roomRepository.save(room);

        cinema.getRooms().add(room);
        cinemaRepository.save(cinema);

        return new ResponseEntity<>(cinemaRepository.findAll(), HttpStatus.CREATED);
    }

    public List<Seat> addSeat(List<Seat> seatList, Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room Not Found"));
        List<Seat> seatsToAdd = new ArrayList<>();

        Iterator<Seat> iterator = seatList.iterator();
        while (iterator.hasNext()) {
            Seat seat = iterator.next();
            seat.setRoom(room);
            seatRepository.save(seat);
            seatsToAdd.add(seat);
        }
        room.getSeats().addAll(seatsToAdd);
        roomRepository.save(room);

        return seatRepository.findAll();
    }

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public ResponseEntity<List<Movie>> addMovie(Movie movie) {
        movieRepository.save(movie);
        return new ResponseEntity<>(movieRepository.findAll(), HttpStatus.CREATED);
    }

    public List<Showtime> addShowtime(Showtime showtime, Long roomId, Long movieId) {

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room Not Found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie Not Found"));

        showtime.setMovie(movie);
        showtime.setRoom(room);
        showtimeRepository.save(showtime);

        room.getShowtime().add(showtime);
        roomRepository.save(room);

        movie.getShowtime().add(showtime);
        movieRepository.save(movie);

        return showtimeRepository.findAll();
    }
}
