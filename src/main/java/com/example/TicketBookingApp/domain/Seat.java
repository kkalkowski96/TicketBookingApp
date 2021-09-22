package com.example.TicketBookingApp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rowNumber;

    private int seatNumber;

    private Boolean availability;

    @ManyToOne
    @JoinColumn(name = "screening_room_id")
    @JsonIgnore
    private ScreeningRoom screeningRoom;
}
