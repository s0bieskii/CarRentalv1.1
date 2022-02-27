package com.car.rental.rental;

import com.car.rental.car.Car;
import com.car.rental.employee.Employee;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String city;
    private String postCode;
    private String street;
    private String phone;
    private boolean deleted;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Car> cars;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Employee> employees;

}
