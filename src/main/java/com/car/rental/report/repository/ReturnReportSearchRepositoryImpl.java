package com.car.rental.report.repository;

import com.car.rental.report.ReturnReport;
import com.car.rental.report.dto.ReturnReportSearchDto;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ReturnReportSearchRepositoryImpl implements ReturnReportSearchRepository{

    public static final Logger LOGGER = Logger.getLogger(ReturnReportSearchRepositoryImpl.class.getName());
    private final EntityManager entityManager;

    public ReturnReportSearchRepositoryImpl(EntityManager entityManager) {
        LOGGER.info("ReturnReportSearchImpl(" + entityManager + ")");
        this.entityManager = entityManager;
    }

    @Override
    public List<ReturnReport> find(ReturnReportSearchDto reportSearchDto) {
        LOGGER.info("find(" + reportSearchDto + ")");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReturnReport> cq = cb.createQuery(ReturnReport.class);
        Root<ReturnReport> report = cq.from(ReturnReport.class);
        ReturnReportCriteriaBuilder returnReportCriteriaBuilder = new ReturnReportCriteriaBuilder(report, cb);

        returnReportCriteriaBuilder.addCriteria("id", reportSearchDto.getId())
                .addCriteria("damaged", reportSearchDto.getDamaged())
                .addCriteria("carId", reportSearchDto.getCarId())
                .addCriteria("employeeId", reportSearchDto.getEmployeeId())
                .addCriteria("deleted", false);

        Predicate predicate = returnReportCriteriaBuilder.getPredicate();
        List<ReturnReport> rentalList;
        if (predicate == null) {
            LOGGER.info("Predicate is null");
            rentalList = entityManager.createQuery(cq.select(report).distinct(true)).getResultList();
        } else {
            LOGGER.info("Predicate created");
            rentalList =
                    entityManager.createQuery(cq.select(report).where(predicate).distinct(true)).getResultList();
        }

        LOGGER.info("Found " + rentalList.size() + " elements");
        return rentalList;
    }
}
