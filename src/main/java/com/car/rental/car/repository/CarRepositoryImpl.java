package com.car.rental.car.repository;

import com.car.rental.car.Car;
import com.car.rental.car.dto.CarSearchDto;
import com.car.rental.rent.Rent;
import com.car.rental.rental.Rental;
import com.car.rental.rental.repository.RentalRepository;
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

public class CarRepositoryImpl implements CarSearchRepository {

    public static final Logger LOGGER = Logger.getLogger(CarRepositoryImpl.class.getName());
    private final EntityManager entityManager;
    private final RentalRepository rentalRepository;

    private CarRepositoryImpl(EntityManager entityManager, RentalRepository rentalRepository) {
        LOGGER.info("create carRepositoryImpl(" + entityManager + " " + rentalRepository);
        this.rentalRepository = rentalRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<Car> find(CarSearchDto carDto) {
        LOGGER.info("Find matching cars find(" + carDto + ")");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> cq = cb.createQuery(Car.class);
        Root<Car> car = cq.from(Car.class);
        CarCriteriaBuilder carCriteriaBuilder = new CarCriteriaBuilder(car, cb);

        carCriteriaBuilder.addCriteria("id", carDto.getId()).addCriteria("brand", carDto.getBrand())
                .addCriteria("model", carDto.getModel())
                .addCriteria("available", true)
                .addCriteria("deleted", false)
                .addCriteria("color", carDto.getColor())
                .addCriteria("registrationYear", carDto.getRegistrationYear())
                .addCriteria("price", carDto.getPrice())
                .addCriteria("segment", carDto.getSegment())
                .addCriteria("doors", carDto.getDoors())
                .addCriteria("seats", carDto.getSeats())
                .addCriteria("fuel", carDto.getFuel())
                .addCriteria("transmission", carDto.getTransmission());
        Predicate predicate = carCriteriaBuilder.getPredicate();
        List<Car> carList;

        if (predicate == null) {
            LOGGER.info("Predicate not contain any search params");
            carList = entityManager.createQuery(cq.select(car).distinct(true)).getResultList();
        } else {
            LOGGER.info("Predicate contain search params");
            carList = entityManager.createQuery(cq.select(car).where(predicate).distinct(true)).getResultList();
        }
        List<Car> carsToReturn = carList;
        if (carDto.getRental() != null) {
            carsToReturn = filterByRentalId(carDto, carsToReturn);
        }
        if (carDto.getStart() != null && carDto.getEnd() != null) {
            carsToReturn = filterByDateAvailable(carDto, carsToReturn);
        }

        LOGGER.info("Found " + carsToReturn.size() + " elements");
        return carsToReturn;
    }

    private List<Car> filterByDateAvailable(CarSearchDto carDto, List<Car> carListToFilter) {
        LOGGER.info("filterByRentalId(" + carDto + ", " + carListToFilter + ")");
        if (carDto.getStart() != null && carDto.getEnd() != null && !carListToFilter.isEmpty()) {
            List<Car> carsToReturn = new ArrayList<>();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Rent> cq = cb.createQuery(Rent.class);
            Root<Rent> rent = cq.from(Rent.class);
            LocalDateTime start = carDto.getStart().minusHours(Config.timeDelayUntilNextRent);
            LocalDateTime end = carDto.getEnd().plusHours(Config.timeDelayUntilNextRent);

            for (Car car : carListToFilter) {
                boolean check = true;
                List<Rent> rents = entityManager.createQuery(cq.select(rent)
                        .where(cb.equal(rent.get("car"), car.getId()))
                        .distinct(true)).getResultList();
                for (Rent rentCheck : rents) {
                    LocalDateTime rentCheckStart = rentCheck.getStart();
                    LocalDateTime rentCheckEnd = rentCheck.getEnd();
                    if ((rentCheck.isDeleted() == false || rentCheck.isConfirmed() == true) && (start.isAfter(rentCheckStart) && start.isBefore(rentCheckEnd)) ||
                            (start.isBefore(rentCheckStart) && end.isAfter(rentCheckEnd)) ||
                            (end.isAfter(rentCheckStart) && end.isBefore(rentCheckEnd))) {
                        check = false;
                    }
                }
                if (check == true) {
                    carsToReturn.add(car);
                }
            }
            carListToFilter = carListToFilter.stream().filter(e -> carsToReturn.contains(e))
                    .collect(Collectors.toList());
            LOGGER.info("Available cars: " + carListToFilter.size());
        }
        return carListToFilter;
    }

    private List<Car> filterByRentalId(CarSearchDto carDto, List<Car> carListToFilter) {
        LOGGER.info("filterByRentalId(" + carDto + ", " + carListToFilter + ")");
        if (carDto.getRental() != null || carListToFilter.isEmpty()) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Rental> cq = cb.createQuery(Rental.class);
            Root<Rental> rental = cq.from(Rental.class);
            CriteriaQuery<Rental> cqRental = cb.createQuery(Rental.class);
            LOGGER.info("Filter cars by rental id");
            cqRental.getRoots().add(rental);
            cqRental.select(rental);
            Predicate predicate = cb.equal(rental.get("id"), carDto.getRental());

            List<Car> carsFromRental =
                    entityManager.createQuery(cq.select(rental).where(predicate).distinct(true)).getResultList()
                            .stream().map(Rental::getCars)
                            .flatMap(List::stream).collect(Collectors.toList());
            carListToFilter =
                    carListToFilter.stream().filter(e -> carsFromRental.contains(e)).collect(Collectors.toList());
            LOGGER.info(
                    "Rental has all cars: " + carsFromRental.size() + " and matching for searching parameter cars: " +
                            carListToFilter.size());
            return carListToFilter;
        }
        return carListToFilter;
    }
}
