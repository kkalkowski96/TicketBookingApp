package com.example.TicketBookingApp.exception;

public class WrongSeatException extends RuntimeException{
    public WrongSeatException() { super("At least 2 space required"); }
}
