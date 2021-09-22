package com.example.TicketBookingApp.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder(toBuilder = true)
public class ScreeningResponse {

    private Long id;

    private LocalDate date;

    private LocalTime time;

    private String title;
}
