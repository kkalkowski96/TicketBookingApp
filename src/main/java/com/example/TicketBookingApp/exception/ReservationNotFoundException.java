package com.example.TicketBookingApp.exception;

public class ReservationNotFoundException extends RuntimeException{
    public ReservationNotFoundException() { super("Reservation not found"); }
}
