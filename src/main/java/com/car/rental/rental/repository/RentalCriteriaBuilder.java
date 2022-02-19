package com.car.rental.rental.repository;

import com.car.rental.car.repository.CarRepositoryImpl;
import com.car.rental.rental.Rental;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Getter;


@Getter
public class RentalCriteriaBuilder {

    public static final Logger LOGGER = Logger.getLogger(CarRepositoryImpl.class.getName());
    private final Root<Rental> rental;
    private final CriteriaBuilder cb;
    private Predicate predicate;

    public RentalCriteriaBuilder(Root<Rental> employee, CriteriaBuilder cb) {
        this.rental = employee;
        this.cb = cb;
    }

    public RentalCriteriaBuilder addCriteria(String fieldName, Object value) {
        if (value == null) {
            return this;
        }
        LOGGER.info("Adding Rental search Criteria [value] to [fieldName] " + value + " to " + fieldName);
        switch (fieldName) {
            case "id":
                if (predicate == null) {
                    predicate = cb.equal(rental.get("id"), (Long) value);
                } else {
                    predicate = cb.and(predicate, cb.equal(rental.get("id"), (Long) value));
                }
                break;
            case "country":
                if (predicate == null) {
                    predicate = cb.equal(rental.get("country"), (String) value);
                } else {
                    predicate = cb.and(predicate, cb.equal(rental.get("country"), (String) value));
                }
                break;
            case "city":
                if (predicate == null) {
                    predicate = cb.equal(rental.get("city"), (String) value);
                } else {
                    predicate = cb.and(predicate, cb.equal(rental.get("city"), (String) value));
                }
                break;
            case "postCode":
                if (predicate == null) {
                    predicate = cb.equal(rental.get("postCode"), (String) value);
                } else {
                    predicate = cb.and(predicate, cb.equal(rental.get("postCode"), (String) value));
                }
                break;
            case "street":
                if (predicate == null) {
                    predicate = cb.like(rental.get("street"), (String) value);
                } else {
                    predicate = cb.and(predicate, cb.like(rental.get("street"), (String) value));
                }
                break;
            case "phone":
                if (predicate == null) {
                    predicate = cb.equal(rental.get("phone"), (String) value);
                } else {
                    predicate = cb.and(predicate, cb.equal(rental.get("phone"), (String) value));
                }
                break;
            case "deleted":
                if (predicate == null) {
                    predicate = cb.equal(rental.get("deleted"), value);
                } else {
                    predicate = cb.and(predicate, cb.equal(rental.get("deleted"), value));
                }
                break;
        }
        return this;
    }
}
