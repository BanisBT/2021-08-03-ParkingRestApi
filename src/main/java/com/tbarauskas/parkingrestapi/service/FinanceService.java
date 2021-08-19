package com.tbarauskas.parkingrestapi.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class FinanceService {

    public BigDecimal getTicketsAmount(BigDecimal costPerHour, LocalDateTime beganDate, LocalDateTime endDate) {
        MathContext mc = new MathContext(2);
        return getTimeDifferenceBetweenDates(beganDate, endDate)
                .multiply(costPerHour.divide(BigDecimal.valueOf(60), 3, RoundingMode.CEILING), mc);
    }

    public BigDecimal getTimeDifferenceBetweenDates(LocalDateTime beganDate, LocalDateTime endDate) {
        Duration duration = Duration.between(beganDate, endDate);
        return BigDecimal.valueOf(duration.toMinutes());
    }
}
