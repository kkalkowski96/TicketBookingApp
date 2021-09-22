package com.example.TicketBookingApp.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder(toBuilder = true)
public class ReservationResponse {

    private Long roomNumber;

    private String movieTitle;

    private LocalDate date;

    private LocalTime time;

    private String personName;

    private String personSurname;

    private LocalDateTime expirationTime;

    private int numberOfTickets;

    private float totalAmountToPay;
}
