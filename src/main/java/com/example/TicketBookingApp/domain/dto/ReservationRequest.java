package com.example.TicketBookingApp.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class ReservationRequest {

    @Pattern(regexp = "^[A-Z][a-z]{2,}",
            message = "The name should start with a capital letter and contain at least 3 characters")
    private String personName;

    @Pattern(regexp = "^[A-Z][a-z]{2,}+((-)[A-Z][a-z]{2,})?",
            message = "Surname must start with a capital letter and contain at least 3 characters")
    private String personSurname;

    private Long screeningId;

    List<TicketRequest> ticketRequests;
}
