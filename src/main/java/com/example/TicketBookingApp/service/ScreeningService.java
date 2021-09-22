package com.example.TicketBookingApp.service;

import com.example.TicketBookingApp.domain.Screening;
import com.example.TicketBookingApp.domain.dto.ScreeningDetailsResponse;
import com.example.TicketBookingApp.domain.dto.ScreeningResponse;
import com.example.TicketBookingApp.exception.ScreeningNotFoundException;
import com.example.TicketBookingApp.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScreeningService {

    private final ScreeningRepository screeningRepository;

    public List<ScreeningResponse> getScreeningByDate(LocalDate date, LocalTime start, LocalTime end) {
        List<Screening> screenings = screeningRepository.findByDateAndStartTimeBetween(date, start, end);
        List<ScreeningResponse> screeningResponses;

        screeningResponses = screenings
                .stream()
                .map(this::mapScreeningToScreeningResponse)
                .collect(Collectors.toList());

        return screeningResponses
                .stream()
                .sorted(Comparator.comparing(ScreeningResponse::getTitle)
                        .thenComparing(ScreeningResponse::getTime))
                .collect(Collectors.toList());
    }

    public ScreeningDetailsResponse getScreeningDetails(Long id) {
        Screening screening = screeningRepository
                .findById(id)
                .orElseThrow(ScreeningNotFoundException::new);

        return ScreeningDetailsResponse
                .builder()
                .id(screening.getId())
                .time(screening.getStartTime())
                .roomNumber(screening.getScreeningRoom().getRoomNumber())
                .seats(screening.getScreeningRoom().getSeatList())
                .build();
    }

    private ScreeningResponse mapScreeningToScreeningResponse(Screening screening) {
        return ScreeningResponse
                .builder()
                .id(screening.getId())
                .date(screening.getDate())
                .time(screening.getStartTime())
                .title(screening.getMovie().getTitle())
                .build();
    }
}
