package com.example.TicketBookingApp.exception;

public class BookingTimeException extends RuntimeException{
    public BookingTimeException() {
        super("Seats can be booked at latest 15 minutes before the screening begins");
    }
}
