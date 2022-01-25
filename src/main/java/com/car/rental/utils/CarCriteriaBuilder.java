package com.car.rental.utils;

import com.car.rental.car.Car;
import com.car.rental.details.CarDetails;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

@Getter
public class CarCriteriaBuilder {
    private CriteriaBuilder cb;
    private Join<CarDetails, Car> source;
    private Predicate predicate;

    public CarCriteriaBuilder(Join<CarDetails, Car> source, CriteriaBuilder cb){
        this.source=source;
        this.cb=cb;
    }



    public CarCriteriaBuilder addCriteria(String fieldName, Object value) {
        if (value == null) {
            return this;
        }
        if (predicate == null) {
            predicate = cb.equal(source.get(fieldName), value);
        } else {
            predicate = cb.and(predicate, cb.equal(source.get(fieldName), value));
        }
        return this;
    }
}
