package com.car.rental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.setProperty("java.runtime.version", "11");
        SpringApplication.run(Application.class, args);


    }

}
