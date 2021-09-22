package com.example.TicketBookingApp.exception;

public class SeatNotFoundException extends RuntimeException{
    public SeatNotFoundException() { super("Seat not found"); }
}
