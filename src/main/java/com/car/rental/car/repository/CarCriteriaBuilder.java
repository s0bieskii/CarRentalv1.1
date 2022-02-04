package com.car.rental.car.repository;

import com.car.rental.car.Car;
import com.car.rental.details.CarDetails;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Getter;

@Getter
public class CarCriteriaBuilder {
    public static final Logger LOGGER = Logger.getLogger(CarCriteriaBuilder.class.getName());
    private CriteriaBuilder cb;
    private Root<Car> car;
    private Predicate predicate;

    public CarCriteriaBuilder(Root<Car> car, CriteriaBuilder cb) {
        this.car = car;
        this.cb = cb;
    }

    public CarCriteriaBuilder addCriteria(String fieldName, Object value) {
        if (value == null) {
            return this;
        }
        if (Arrays.stream(Car.class.getDeclaredFields()).map(field -> field.getName())
                .collect(Collectors.toList()).contains(fieldName)) {
            if (predicate == null) {
                predicate = cb.equal(car.get(fieldName), value);
            } else {
                predicate = cb.and(predicate, cb.equal(car.get(fieldName), value));
            }
        } else if (Arrays.stream(CarDetails.class.getDeclaredFields()).map(field -> field.getName())
                .collect(Collectors.toList()).contains(fieldName)) {
            if (predicate == null) {
                predicate = cb.equal(car.get("carDetails").get(fieldName), value);
            } else {
                predicate = cb.and(predicate, cb.equal(car.get("carDetails").get(fieldName), value));
            }
        }

        return this;
    }
}
