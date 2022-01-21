package com.car.rental.rent;

import com.car.rental.car.Car;
import com.car.rental.report.ReturnReport;
import com.car.rental.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(fetch = FetchType.LAZY,orphanRemoval = false)
    private Car car;
    @ManyToOne
    private User user;
    @OneToOne
    private ReturnReport report;
    private LocalDateTime start;
    private LocalDateTime end;
    private String comment;
    private Double finalPrice;
    private boolean confirmed;
    private boolean returned;
}
