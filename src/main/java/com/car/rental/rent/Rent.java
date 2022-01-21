package com.car.rental.rent;

import com.car.rental.car.Car;
import com.car.rental.report.ReturnReport;
import com.car.rental.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(orphanRemoval = false)
    private Car car;
    private User user;
    private ReturnReport report;
    private LocalDateTime start;
    private LocalDateTime end;
}
