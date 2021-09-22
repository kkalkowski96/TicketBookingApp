package com.example.TicketBookingApp.repository;

import com.example.TicketBookingApp.domain.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    Optional<Screening> findById(Long id);
    List<Screening> findByDateAndStartTimeBetween(LocalDate date, LocalTime t1, LocalTime t2);
}
