package com.tbarauskas.parkingrestapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FinanceServiceTest {

    @InjectMocks
    private FinanceService financeService;

    @Test
    void testGetTimeDifferenceBetweenDates() {
        LocalDateTime now = LocalDateTime.now();

        BigDecimal difference = financeService.getTimeDifferenceBetweenDates(now, now.plusMinutes(30));

        assertEquals(30, difference.intValue());
    }

    @Test
    void testGetTicketsAmount() {
        LocalDateTime now = LocalDateTime.now();
        BigDecimal costPerHour = BigDecimal.valueOf(5);

        BigDecimal ticketAmount = financeService.getTicketsAmount(costPerHour, now, now.plusMinutes(30));
        String amount = ticketAmount.toString();

        assertEquals("2.5", amount);
    }
}
