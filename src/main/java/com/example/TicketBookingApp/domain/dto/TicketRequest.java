package com.example.TicketBookingApp.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class TicketRequest {

    private Long seatId;

    private String ticketType;
}
