package com.car.rental.utils;

import com.car.rental.car.Car;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.logging.Logger;

@Getter
public class CarCriteriaBuilder {
    public static final Logger LOGGER = Logger.getLogger(CarCriteriaBuilder.class.getName());
    private CriteriaBuilder cb;
    private Root<Car> car;
    private Predicate predicate;

    public CarCriteriaBuilder(Root<Car> car, CriteriaBuilder cb){
        this.car=car;
        this.cb=cb;
    }

        public CarCriteriaBuilder addCriteria(String fieldName, Object value) {
            if (value == null) {
                return this;
            }
            LOGGER.info("Adding Car search Criteria [value] to [fieldName] "+value+" to "+fieldName);
            if (predicate == null) {
                if(fieldName.equals("rental")){
                    predicate= cb.equal(car.get("rental").get("id"), (Integer)value);
                } else if(fieldName.equals("color")){
                    predicate = cb.equal(car.get("carDetails").get(fieldName), value);
                } else if(fieldName.equals("registrationYear")){
                    predicate = cb.equal(car.get("carDetails").get(fieldName), value);
                } else if(fieldName.equals("price")){
                    predicate=cb.lessThanOrEqualTo(car.get("carDetails").get(fieldName), (Double)value);
                } else if(fieldName.equals("segment")){
                    predicate=cb.equal(car.get("carDetails").get(fieldName), (String)value);
                } else if(fieldName.equals("doors")){
                    predicate=cb.equal(car.get("carDetails").get(fieldName), value);
                } else if(fieldName.equals("seats")){
                    predicate=cb.equal(car.get("carDetails").get(fieldName), value);
                } else if(fieldName.equals("fuel")){
                    predicate=cb.equal(car.get("carDetails").get(fieldName), (String)value);
                } else if(fieldName.equals("transmission")){
                    predicate=cb.equal(car.get("carDetails").get(fieldName), (String)value);
                } else if(fieldName.equals("registrationYear")){
                    predicate=cb.greaterThanOrEqualTo(car.get("carDetails").get(fieldName), (Integer)value);
                } else {
                    predicate=cb.equal(car.get(fieldName), value);
                }
            } else {
                if(fieldName.equals("rental")){
                    predicate=cb.and(predicate, cb.equal(car.get("rental").get("id"), (Integer)value));
                } else if(fieldName.equals("color")){
                    predicate=cb.and(predicate, cb.equal(car.get("carDetails").get(fieldName), value));
                } else if(fieldName.equals("registrationYear")){
                    predicate=cb.and(predicate, cb.equal(car.get("carDetails").get(fieldName), value));
                } else if(fieldName.equals("price")){
                    predicate=cb.and(predicate, cb.lessThanOrEqualTo(car.get("carDetails").get(fieldName), (Double)value));
                } else if(fieldName.equals("segment")){
                    predicate=cb.and(predicate, cb.equal(car.get("carDetails").get(fieldName), (String)value));
                } else if(fieldName.equals("doors")){
                    predicate=cb.and(predicate, cb.equal(car.get("carDetails").get(fieldName), value));
                } else if(fieldName.equals("seats")){
                    predicate=cb.and(predicate, cb.equal(car.get("carDetails").get(fieldName), value));
                } else if(fieldName.equals("fuel")){
                    predicate=cb.and(predicate, cb.equal(car.get("carDetails").get(fieldName), (String)value));
                } else if(fieldName.equals("transmission")){
                    predicate=cb.and(predicate, cb.equal(car.get("carDetails").get(fieldName), (String)value));
                } else {
                    predicate=cb.and(predicate, cb.equal(car.get(fieldName), value));
                }
            }
            return this;
        }
}
