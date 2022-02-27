package com.car.rental.report.repository;

import com.car.rental.car.repository.CarRepositoryImpl;
import com.car.rental.report.ReturnReport;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Getter;

@Getter
public class ReturnReportCriteriaBuilder {

    public static final Logger LOGGER = Logger.getLogger(CarRepositoryImpl.class.getName());
    private final Root<ReturnReport> report;
    private final CriteriaBuilder cb;
    private Predicate predicate;

    public ReturnReportCriteriaBuilder(Root<ReturnReport> report, CriteriaBuilder cb) {
        this.report = report;
        this.cb = cb;
    }

    public ReturnReportCriteriaBuilder addCriteria(String fieldName, Object value) {
        if (value == null) {
            return this;
        }
        LOGGER.info("Adding Return Report search Criteria [value] to [fieldName] " + value + " to " + fieldName);
        switch (fieldName) {
            case "id":
                if (predicate == null) {
                    predicate = cb.equal(report.get("id"), value);
                } else {
                    predicate = cb.and(predicate, cb.equal(report.get("id"), value));
                }
                break;
            case "damaged":
                if (predicate == null) {
                    predicate = cb.equal(report.get("damaged"), value);
                } else {
                    predicate = cb.and(predicate, cb.equal(report.get("damaged"), value));
                }
                break;
            case "carId":
                if (predicate == null) {
                    predicate = cb.equal(report.get("car").get("id"), value);
                } else {
                    predicate = cb.and(predicate, cb.equal(report.get("car").get("id"), value));
                }
                break;
            case "employeeId":
                if (predicate == null) {
                    predicate = cb.equal(report.get("employee").get("id"), value);
                } else {
                    predicate = cb.and(predicate, cb.equal(report.get("employee").get("id"), value));
                }
                break;
            case "deleted":
                if (predicate == null) {
                    predicate = cb.equal(report.get("deleted"), value);
                } else {
                    predicate = cb.and(predicate, cb.equal(report.get("deleted"), value));
                }
                break;
        }
        return this;
    }
}
