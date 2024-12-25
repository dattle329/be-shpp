package com.own.moviebooking2.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private int duration;

    private LocalDate releaseDate;

    @OneToMany(mappedBy = "movie")
    private Set<Showtime> showtime;
}
