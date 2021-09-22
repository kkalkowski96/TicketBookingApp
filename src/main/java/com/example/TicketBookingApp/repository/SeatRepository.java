package com.example.TicketBookingApp.repository;

import com.example.TicketBookingApp.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findById(Long id);
    Seat findByScreeningRoom_RoomNumberAndRowNumberAndSeatNumber(long roomNumber, int rowNumber, int seatNumber);
}
