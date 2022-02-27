package com.car.rental.rent.repository;

import com.car.rental.car.repository.CarRepositoryImpl;
import com.car.rental.rent.Rent;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Getter;

@Getter
public class RentCriteriaBuilder {
    public static final Logger LOGGER = Logger.getLogger(CarRepositoryImpl.class.getName());
    private final Root<Rent> rent;
    private final CriteriaBuilder cb;
    private Predicate predicate;

    public RentCriteriaBuilder(Root<Rent> rent, CriteriaBuilder cb) {
        this.rent = rent;
        this.cb = cb;
    }

    public RentCriteriaBuilder addCriteria(String fieldName, Object value) {
        if (value == null) {
            return this;
        }
        LOGGER.info("Adding Rent search Criteria [value] to [fieldName] " + value + " to " + fieldName);
        switch (fieldName) {
            case "id":
                if (predicate == null) {
                    predicate = cb.equal(rent.get("id"), (Long) value);
                } else {
                    predicate = cb.and(predicate, cb.equal(rent.get("id"), (Long) value));
                }
                break;
            case "carId":
                if (predicate == null) {
                    predicate = cb.equal(rent.get("car").get("id"), (Long) value);
                } else {
                    predicate = cb.and(predicate, cb.equal(rent.get("car").get("id"), (Long) value));
                }
                break;
            case "userId":
                if (predicate == null) {
                    predicate = cb.equal(rent.get("user").get("id"), (Long) value);
                } else {
                    predicate = cb.and(predicate, cb.equal(rent.get("user").get("id"), (Long) value));
                }
                break;
            case "confirmed":
                if (predicate == null) {
                    predicate = cb.equal(rent.get("confirmed"), (Boolean) value);
                } else {
                    predicate = cb.and(predicate, cb.equal(rent.get("confirmed"), (Boolean) value));
                }
                break;
            case "returned":
                if (predicate == null) {
                    predicate = cb.equal(rent.get("returned"), (Boolean) value);
                } else {
                    predicate = cb.and(predicate, cb.equal(rent.get("returned"), (Boolean) value));
                }
                break;
            case "damaged":
                if (predicate == null) {
                    predicate = cb.equal(rent.get("report").get("damaged"), (Boolean) value);
                } else {
                    predicate = cb.and(predicate, cb.equal(rent.get("report").get("damaged"), (Boolean) value));
                }
                break;
            case "deleted":
                if (predicate == null) {
                    predicate = cb.equal(rent.get("deleted"), (Boolean) value);
                } else {
                    predicate = cb.and(predicate, cb.equal(rent.get("deleted"), (Boolean) value));
                }
                break;
        }
        return this;
    }


}
