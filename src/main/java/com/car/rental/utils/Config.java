package com.car.rental.utils;

import org.springframework.stereotype.Component;

@Component
public class Config {
    public static final String applicationPath = "http://localhost:8080";
    public static final String globalLocalDataTimeFormat = "yyyy-MM-dd HH:mm";
    public static final String globalLocalDataFormat = "yyyy-MM-dd";
    /**
     * Time in hours after which next customer can rent Car
     */
    public static int timeDelayUntilNextRent = 12;
}
