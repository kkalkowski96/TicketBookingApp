package com.example.TicketBookingApp.controller;

import com.example.TicketBookingApp.domain.Reservation;
import com.example.TicketBookingApp.domain.dto.ReservationRequest;
import com.example.TicketBookingApp.domain.dto.ReservationResponse;
import com.example.TicketBookingApp.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid
                                                                 @RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity
                .ok(reservationService
                        .createReservation(reservationRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> showFinalReservation(@PathVariable Long id) {
        return ResponseEntity
                .ok(reservationService
                        .showFinalReservation(id));
    }
}
