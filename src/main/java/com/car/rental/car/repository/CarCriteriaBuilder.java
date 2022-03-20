package com.car.rental.car.repository;

import com.car.rental.car.Car;
import com.car.rental.details.CarDetails;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Getter;

@Getter
public class CarCriteriaBuilder {
    public static final Logger LOGGER = Logger.getLogger(CarCriteriaBuilder.class.getName());
    public final List<String> customFields = new ArrayList<>();
    private final CriteriaBuilder cb;
    private final Root<Car> car;
    private Predicate predicate;

    public CarCriteriaBuilder(Root<Car> car, CriteriaBuilder cb) {
        LOGGER.info("CarCriteriaBuilder(" + car + ", CriteriaBuilder " + cb + ")");
        this.car = car;
        this.cb = cb;
        initializeCustomFields();
    }

    public CarCriteriaBuilder addCriteria(String fieldName, Object value) {
        LOGGER.info("AddCriteria to: " + fieldName + " " + value);
        if (value == null) {
            return this;
        }
        if(fieldName == "price"){
            BigDecimal check = (BigDecimal)value;
            if( check.equals(BigDecimal.valueOf(0))){
                value = BigDecimal.valueOf(Double.MAX_VALUE);
            }
        }
        if (customFields.contains(fieldName)) {
            customFieldsSearch(fieldName, value);
            return this;
        }
        if (Arrays.stream(Car.class.getDeclaredFields()).map(Field::getName)
                .collect(Collectors.toList()).contains(fieldName)) {
            if (predicate == null) {
                predicate = cb.equal(car.get(fieldName), value);
            } else {
                predicate = cb.and(predicate, cb.equal(car.get(fieldName), value));
            }
        } else if (Arrays.stream(CarDetails.class.getDeclaredFields()).map(Field::getName)
                .collect(Collectors.toList()).contains(fieldName)) {
            if (predicate == null) {
                predicate = cb.equal(car.get("carDetails").get(fieldName), value);
            } else {
                predicate = cb.and(predicate, cb.equal(car.get("carDetails").get(fieldName), value));
            }
        }
        return this;
    }

    private void customFieldsSearch(String fieldName, Object value) {
        if (predicate == null) {
            if (fieldName.equals("registrationYear")) {
                predicate = cb.greaterThanOrEqualTo(car.get("carDetails").get(fieldName), (Integer) value);
            } else if (fieldName.equals("price")) {
                predicate = cb.lessThanOrEqualTo(car.get("carDetails").get(fieldName), (BigDecimal) value);
            }
        } else {
            if (fieldName.equals("registrationYear")) {
                predicate = cb.and(predicate,
                        cb.greaterThanOrEqualTo(car.get("carDetails").get(fieldName), (Integer) value));
            } else if (fieldName.equals("price")) {
                predicate = cb.and(predicate,
                        cb.lessThanOrEqualTo(car.get("carDetails").get(fieldName), (BigDecimal) value));
            }
        }
    }

    private void initializeCustomFields() {
        customFields.add("registrationYear");
        customFields.add("price");
    }
}
