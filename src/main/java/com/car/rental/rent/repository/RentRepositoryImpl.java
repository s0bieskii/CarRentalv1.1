package com.car.rental.rent.repository;

import com.car.rental.rent.Rent;
import com.car.rental.rent.dto.RentSearchDto;
import com.car.rental.utils.Config;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RentRepositoryImpl implements RentSearchRepository {

    public static final Logger LOGGER = Logger.getLogger(RentRepositoryImpl.class.getName());
    private final EntityManager entityManager;

    public RentRepositoryImpl(EntityManager entityManager) {
        LOGGER.info("RentRepositoryImpl(" + entityManager + ")");
        this.entityManager = entityManager;
    }

    @Override
    public List<Rent> find(RentSearchDto rentSearchDto) {
        LOGGER.info("find(" + rentSearchDto + ")");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Rent> cq = cb.createQuery(Rent.class);
        Root<Rent> employee = cq.from(Rent.class);
        RentCriteriaBuilder rentCriteriaBuilder = new RentCriteriaBuilder(employee, cb);

        rentCriteriaBuilder.addCriteria("id", rentSearchDto.getId())
                .addCriteria("carId", rentSearchDto.getCarId())
                .addCriteria("userId", rentSearchDto.getUserId())
                .addCriteria("confirmed", rentSearchDto.getConfirmed())
                .addCriteria("returned", rentSearchDto.getReturned())
                .addCriteria("damaged", rentSearchDto.getDamaged())
                .addCriteria("deleted", false);

        Predicate predicate = rentCriteriaBuilder.getPredicate();
        List<Rent> rentList;
        if (predicate == null) {
            LOGGER.info("Predicate is null");
            rentList = entityManager.createQuery(cq.select(employee).distinct(true)).getResultList();
        } else {
            LOGGER.info("Predicate created");
            rentList =
                    entityManager.createQuery(cq.select(employee).where(predicate).distinct(true)).getResultList();
        }
        if (rentSearchDto.getStart() != null && rentSearchDto.getEnd() != null) {
            rentList = filterByDateAvailable(rentSearchDto, rentList);
        }
        LOGGER.info("Found " + rentList.size() + " elements");
        return rentList;
    }

    private List<Rent> filterByDateAvailable(RentSearchDto rentDto, List<Rent> rentsListToFilter) {
        LOGGER.info("filterByRentalId(" + rentDto + ", " + rentsListToFilter + ")");
        if (rentDto.getStart() != null && rentDto.getEnd() != null && !rentsListToFilter.isEmpty()) {
            List<Rent> rentsToReturn = new ArrayList<>();
            LocalDateTime start = rentDto.getStart().minusHours(Config.TIME_DELAY_UNTIL_NEXT_RENT);
            LocalDateTime end = rentDto.getEnd().plusHours(Config.TIME_DELAY_UNTIL_NEXT_RENT);
            for (Rent rentToCheck : rentsListToFilter) {
                LocalDateTime rentCheckStart = rentToCheck.getStartDate();
                LocalDateTime rentCheckEnd = rentToCheck.getEndDate();
                boolean check = !rentToCheck.isDeleted() && !rentToCheck.isConfirmed() &&
                        (!start.isAfter(rentCheckStart) || !start.isBefore(rentCheckEnd)) &&
                        (!start.isBefore(rentCheckStart) || !end.isAfter(rentCheckEnd)) &&
                        (!end.isAfter(rentCheckStart) || !end.isBefore(rentCheckEnd));
                if (check) {
                    rentsToReturn.add(rentToCheck);
                }
            }
            rentsListToFilter = rentsListToFilter.stream().filter(rentsToReturn::contains)
                    .collect(Collectors.toList());
            LOGGER.info("Available cars: " + rentsListToFilter.size());
        }
        return rentsListToFilter;
    }
}
