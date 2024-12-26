package com.own.moviebooking2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDateTime startTime;

    private long price;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;

    @OneToMany(mappedBy = "showtime")
    private Set<Ticket> tickets;

    @PrePersist
    private void setPrice() {
        LocalTime time = startTime.toLocalTime();
        LocalTime threshold = LocalTime.of(22, 0);
        if (time.isAfter(threshold)) {
            this.price = price - 25000;
        }
    }
}