package com.example.TicketBookingApp.exception;

import com.example.TicketBookingApp.domain.Seat;

public class SeatNotAvailableException extends RuntimeException{
    public SeatNotAvailableException(Seat seat) {
        super(String.format("Seat %d in row %d has already been reserved", seat.getSeatNumber(), seat.getRowNumber()));
    }
}
