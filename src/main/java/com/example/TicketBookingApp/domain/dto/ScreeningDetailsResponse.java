package com.example.TicketBookingApp.domain.dto;

import com.example.TicketBookingApp.domain.Seat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class ScreeningDetailsResponse {

    private Long id;

    private LocalTime time;

    private Long roomNumber;

    private List<Seat> seats;
}
