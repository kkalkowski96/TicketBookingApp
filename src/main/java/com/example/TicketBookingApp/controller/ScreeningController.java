package com.example.TicketBookingApp.controller;

import com.example.TicketBookingApp.domain.dto.ScreeningDetailsResponse;
import com.example.TicketBookingApp.domain.dto.ScreeningResponse;
import com.example.TicketBookingApp.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/screenings")
@RequiredArgsConstructor
public class ScreeningController {

    private final ScreeningService screeningService;

    @GetMapping
    public ResponseEntity<List<ScreeningResponse>> getScreeningsByDate(@RequestParam
                                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                                       @RequestParam
                                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime start,
                                                                       @RequestParam
                                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)LocalTime end){
        return ResponseEntity
                .ok(screeningService
                        .getScreeningByDate(date, start, end));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreeningDetailsResponse> getScreeningById(@PathVariable Long id){
        return ResponseEntity
                .ok(screeningService
                        .getScreeningDetails(id));
    }
}
