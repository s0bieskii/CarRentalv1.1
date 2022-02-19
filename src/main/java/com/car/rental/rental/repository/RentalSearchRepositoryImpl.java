package com.car.rental.rental.repository;

import com.car.rental.rental.Rental;
import com.car.rental.rental.dto.RentalSearchDto;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RentalSearchRepositoryImpl implements RentalSearchRepository {

    public static final Logger LOGGER = Logger.getLogger(RentalSearchRepositoryImpl.class.getName());
    private final EntityManager entityManager;

    public RentalSearchRepositoryImpl(EntityManager entityManager) {
        LOGGER.info("RentalSearchRepositoryImpl(" + entityManager + ")");
        this.entityManager = entityManager;
    }

    @Override
    public List<Rental> find(RentalSearchDto rentalSearchDto) {
        LOGGER.info("find(" + rentalSearchDto + ")");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Rental> cq = cb.createQuery(Rental.class);
        Root<Rental> rental = cq.from(Rental.class);
        RentalCriteriaBuilder rentalCriteriaBuilder = new RentalCriteriaBuilder(rental, cb);

        rentalCriteriaBuilder.addCriteria("id", rentalSearchDto.getId())
                .addCriteria("country", rentalSearchDto.getCountry())
                .addCriteria("city", rentalSearchDto.getCity())
                .addCriteria("postCode", rentalSearchDto.getPostCode())
                .addCriteria("street", rentalSearchDto.getStreet())
                .addCriteria("street", rentalSearchDto.getPhone())
                .addCriteria("deleted", false);

        Predicate predicate = rentalCriteriaBuilder.getPredicate();
        List<Rental> rentalList;
        if (predicate == null) {
            LOGGER.info("Predicate is null");
            rentalList = entityManager.createQuery(cq.select(rental).distinct(true)).getResultList();
        } else {
            LOGGER.info("Predicate created");
            rentalList =
                    entityManager.createQuery(cq.select(rental).where(predicate).distinct(true)).getResultList();
        }

        LOGGER.info("Found " + rentalList.size() + " elements");
        return rentalList;
    }
}
