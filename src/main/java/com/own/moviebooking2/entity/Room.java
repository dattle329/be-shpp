package com.own.moviebooking2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    @JsonIgnore
    private Cinema cinema;

    @OneToMany(mappedBy = "room")
    private Set<Seat> seats;

    @OneToMany(mappedBy = "room")
    private Set<Showtime> showtime;
}
