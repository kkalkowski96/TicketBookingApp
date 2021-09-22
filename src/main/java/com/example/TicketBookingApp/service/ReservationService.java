package com.example.TicketBookingApp.service;

import com.example.TicketBookingApp.configuration.TicketTypeConfiguration;
import com.example.TicketBookingApp.domain.Reservation;
import com.example.TicketBookingApp.domain.Screening;
import com.example.TicketBookingApp.domain.Seat;
import com.example.TicketBookingApp.domain.Ticket;
import com.example.TicketBookingApp.domain.dto.ReservationRequest;
import com.example.TicketBookingApp.domain.dto.ReservationResponse;
import com.example.TicketBookingApp.domain.dto.TicketRequest;
import com.example.TicketBookingApp.exception.*;
import com.example.TicketBookingApp.repository.ReservationRepository;
import com.example.TicketBookingApp.repository.ScreeningRepository;
import com.example.TicketBookingApp.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
    private final TicketTypeConfiguration ticketTypeConfiguration;

    private final int MAX_TIME_BEFORE_START_SCREENING = 15;
    private final int DISTANCE_OF_ONE_PLACE = 1;
    private final int DISTANCE_OF_TWO_PLACES = 2;

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Screening screening = screeningRepository
                .findById(reservationRequest.getScreeningId())
                .orElseThrow(ScreeningNotFoundException::new);

        Reservation reservation = Reservation
                .builder()
                .date(screening.getDate())
                .time(screening.getStartTime())
                .screening(screening)
                .personName(reservationRequest.getPersonName())
                .personSurname(reservationRequest.getPersonSurname())
                .expirationTime(screening.getStartTime().plus(Duration.ofMinutes(30)).atDate(screening.getDate()))
                .build();

        reservation.setTickets(reservationRequest
                .getTicketRequests()
                .stream()
                .map(ticketRequest -> createTicketForReservation(ticketRequest, reservation))
                .collect(Collectors.toList()));


        reservation.setTotalAmountToPay((float) reservation
                .getTickets()
                .stream()
                .mapToDouble(Ticket::getTicketPrice)
                .sum()
        );

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime screeningDateTime = LocalDateTime.of(screening.getDate(), screening.getStartTime());

        if (Duration.between(localDateTime, screeningDateTime).toMinutes() > MAX_TIME_BEFORE_START_SCREENING) {
            reservationRepository.save(reservation);
            return ReservationResponse
                    .builder()
                    .movieTitle(reservation.getScreening().getMovie().getTitle())
                    .date(reservation.getDate())
                    .time(reservation.getTime())
                    .personName(reservation.getPersonName())
                    .personSurname(reservation.getPersonSurname())
                    .expirationTime(reservation.getExpirationTime())
                    .numberOfTickets(reservation.getTickets().size())
                    .totalAmountToPay(reservation.getTotalAmountToPay())
                    .roomNumber(reservation.getScreening().getScreeningRoom().getRoomNumber())
                    .build();
        } else
            throw new BookingTimeException();
    }

    private Boolean checkAvailabilityOfAdjacentSeat(Seat seat) {
        Seat secondSeatFromTheLeft = seatRepository
                .findByScreeningRoom_RoomNumberAndRowNumberAndSeatNumber(seat.getScreeningRoom().getRoomNumber(), seat.getRowNumber(), seat.getSeatNumber() - DISTANCE_OF_TWO_PLACES);
        Seat leftAdjacentSeat = seatRepository
                .findByScreeningRoom_RoomNumberAndRowNumberAndSeatNumber(seat.getScreeningRoom().getRoomNumber(), seat.getRowNumber(), seat.getSeatNumber() - DISTANCE_OF_ONE_PLACE);
        Seat secondSeatFromTheRight = seatRepository
                .findByScreeningRoom_RoomNumberAndRowNumberAndSeatNumber(seat.getScreeningRoom().getRoomNumber(), seat.getRowNumber(), seat.getSeatNumber() + DISTANCE_OF_TWO_PLACES);
        Seat rightAdjacentSeat = seatRepository
                .findByScreeningRoom_RoomNumberAndRowNumberAndSeatNumber(seat.getScreeningRoom().getRoomNumber(), seat.getRowNumber(), seat.getSeatNumber() + DISTANCE_OF_ONE_PLACE);

        if (!ObjectUtils.isEmpty(secondSeatFromTheLeft)) {
            return secondSeatFromTheLeft.getAvailability() || !leftAdjacentSeat.getAvailability();
        }
        if (!ObjectUtils.isEmpty(secondSeatFromTheRight)) {
            return secondSeatFromTheRight.getAvailability() || !rightAdjacentSeat.getAvailability();
        }
        return true;
    }

    public Reservation showFinalReservation(Long id) {
        return reservationRepository
                .findById(id)
                .orElseThrow(ReservationNotFoundException::new);
    }

    private Ticket createTicketForReservation(TicketRequest ticketRequest, Reservation reservation) {
        Seat seat = seatRepository
                .findById(ticketRequest
                        .getSeatId())
                .orElseThrow(SeatNotFoundException::new);
        if (seat.getAvailability()
                && checkAvailabilityOfAdjacentSeat(seat)
                && ticketTypeConfiguration.getTicketConfiguration().containsKey(ticketRequest.getTicketType())) {
            seat
                    .setAvailability(false);
            return Ticket
                    .builder()
                    .seat(seat)
                    .ticketType(ticketRequest.getTicketType())
                    .ticketPrice(ticketTypeConfiguration.getTicketConfiguration().get(ticketRequest.getTicketType()))
                    .reservation(reservation)
                    .build();


        } else {
            if (!seat.getAvailability())
                throw new SeatNotAvailableException(seat);
            else
                throw new WrongSeatException();
        }
    }
}
