package com.car.rental.car;

import com.car.rental.details.CarDetails;
import com.car.rental.rental.Rental;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String brand;
    private String model;
    private boolean available;
    private boolean deleted;
    @OneToOne(cascade = CascadeType.ALL)
    private CarDetails carDetails;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rental_id")
    private Rental rental;

}
