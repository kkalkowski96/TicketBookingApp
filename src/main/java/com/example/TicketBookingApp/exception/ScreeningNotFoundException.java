package com.example.TicketBookingApp.exception;

public class ScreeningNotFoundException extends RuntimeException{
    public ScreeningNotFoundException() { super("Screening not found"); }
}
