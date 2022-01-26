package com.car.rental.car;

import com.car.rental.car.dto.CarSearchDto;
import com.car.rental.utils.CarCriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

public class CarRepositoryImpl implements CarSearchRepository{
    public static final Logger LOGGER = Logger.getLogger(CarRepositoryImpl.class.getName());

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Car> find(CarSearchDto carDto) {
        LOGGER.info("Trying to find car with parameters : "+carDto);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> cq = cb.createQuery(Car.class);
        Root<Car> car = cq.from(Car.class);
        CarCriteriaBuilder carCriteriaBuilder = new CarCriteriaBuilder(car, cb);

        carCriteriaBuilder.addCriteria("id", carDto.getId())
                .addCriteria("brand", carDto.getBrand())
                .addCriteria("model", carDto.getModel())
                .addCriteria("available", carDto.getAvailable())
                .addCriteria("deleted", carDto.getDeleted())
                .addCriteria("rental", carDto.getRental())
                .addCriteria("color", carDto.getColor())
                .addCriteria("registrationYear", carDto.getRegistrationYear())
                .addCriteria("price", carDto.getPrice())
                .addCriteria("segment", carDto.getSegment())
                .addCriteria("doors", carDto.getDoors())
                .addCriteria("seats", carDto.getSeats())
                .addCriteria("fuel", carDto.getFuel())
                .addCriteria("transmission", carDto.getTransmission());
        Predicate predicate=carCriteriaBuilder.getPredicate();
        List<Car> carList;

        if(predicate==null){
            LOGGER.info("Predicate is null");
            carList=entityManager.createQuery(cq.select(car).distinct(true)).getResultList();
        } else {
            LOGGER.info("Predicate created");
            carList=entityManager.createQuery(cq.select(car).where(predicate).distinct(true)).getResultList();
        }
        LOGGER.info("Found "+carList.size()+" elements");
        return carList;
    }
}
