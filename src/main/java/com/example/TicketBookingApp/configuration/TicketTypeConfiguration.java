package com.example.TicketBookingApp.configuration;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@RequiredArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "tickets")
public class TicketTypeConfiguration {
    private Map<String, Float> ticketConfiguration;
}
