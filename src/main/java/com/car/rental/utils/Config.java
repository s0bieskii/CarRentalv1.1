package com.car.rental.utils;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public final class Config {
    public static final String APPLICATION_PATH = "http://localhost:8080";
    public static final String GLOBAL_LOCAL_DATA_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String GLOBAL_LOCAL_DATA_FORMAT = "yyyy-MM-dd";
    /**
     * Time in hours after which next customer can rent Car
     */
    public static final int TIME_DELAY_UNTIL_NEXT_RENT = 12;

    public static final Pattern SIMPLE_VIN_PATTERN = Pattern.compile("^([A-Z0-9]){17}$");
}
