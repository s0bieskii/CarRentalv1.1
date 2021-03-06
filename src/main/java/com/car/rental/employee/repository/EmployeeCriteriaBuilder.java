package com.car.rental.employee.repository;

import com.car.rental.car.repository.CarRepositoryImpl;
import com.car.rental.employee.Employee;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Getter;

@Getter
public class EmployeeCriteriaBuilder {

    public static final Logger LOGGER = Logger.getLogger(CarRepositoryImpl.class.getName());
    private final Root<Employee> employee;
    private final CriteriaBuilder cb;
    private Predicate predicate;

    public EmployeeCriteriaBuilder(Root<Employee> employee, CriteriaBuilder cb) {
        this.employee = employee;
        this.cb = cb;
    }

    public EmployeeCriteriaBuilder addCriteria(String fieldName, Object value) {
        if (value == null) {
            return this;
        }
        LOGGER.info("Adding Car search Criteria [value] to [fieldName] " + value + " to " + fieldName);
        switch (fieldName) {
            case "id":
                if (predicate == null) {
                    predicate = cb.equal(employee.get("id"), value);
                } else {
                    predicate = cb.and(predicate, cb.equal(employee.get("id"), value));
                }
                break;
            case "firstName":
                if (predicate == null) {
                    predicate = cb.equal(employee.get("firstName"), value);
                } else {
                    predicate = cb.and(predicate, cb.equal(employee.get("firstName"), value));
                }
                break;
            case "lastName":
                if (predicate == null) {
                    predicate = cb.equal(employee.get("lastName"), value);
                } else {
                    predicate = cb.and(predicate, cb.equal(employee.get("lastName"), value));
                }
                break;
            case "employmentPosition":
                if (predicate == null) {
                    predicate = cb.equal(employee.get("employmentPosition"), value);
                } else {
                    predicate = cb.and(predicate, cb.equal(employee.get("employmentPosition"), value));
                }
                break;
            case "deleted":
                if (predicate == null) {
                    predicate = cb.equal(employee.get("deleted"), value);
                } else {
                    predicate = cb.and(predicate, cb.equal(employee.get("deleted"), value));
                }
                break;
        }
        return this;
    }


}
