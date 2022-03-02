package com.car.rental.report;

import com.car.rental.car.Car;
import com.car.rental.employee.Employee;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class ReturnReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String note;
    private boolean damaged;
    @OneToOne(orphanRemoval = true)
    private Car car;
    @OneToOne(orphanRemoval = true)
    private Employee employee;
    private boolean deleted;

}
