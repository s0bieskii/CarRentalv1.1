package com.car.rental.utils;

import org.springframework.stereotype.Component;

@Component
public class Config {
    public static final String applicationPath="http://localhost:8080";
    /**
     * Time in hours after which next customer can rent Car
     */
    public static int timeDelayUntilNextRent = 12;
}
