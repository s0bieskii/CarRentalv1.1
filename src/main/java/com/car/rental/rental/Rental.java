package com.car.rental.rental;

import com.car.rental.car.Car;
import com.car.rental.employee.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String country;
    private String city;
    private String postCode;
    private String street;
    private String phone;
    private boolean deleted;
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Car> cars;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Employee> employees;

}
