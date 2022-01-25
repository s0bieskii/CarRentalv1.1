package com.car.rental.car;

import com.car.rental.car.dto.CarSearchDto;
import com.car.rental.details.CarDetails;
import com.car.rental.utils.CarCriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.List;

public class CarRepositoryImpl implements CarSearchRepository{

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Car> find(CarSearchDto carDto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> cq = cb.createQuery(Car.class);
        Predicate predicate;
        List<Car> searchResult;
        Root<Car> car = cq.from(Car.class);
        Join<CarDetails, Car> join=car.join("carDetails", JoinType.LEFT);
        CarCriteriaBuilder criteriaBuilder=new CarCriteriaBuilder(join, cb);

        for(Field field:carDto.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {criteriaBuilder.addCriteria(field.getName(),field.get(carDto));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        predicate=criteriaBuilder.getPredicate();

        if(predicate==null){
            searchResult=entityManager.createQuery(cq.select(join).distinct(true)).getResultList();
        } else {
            searchResult=entityManager.createQuery(cq.select(join).where(predicate).distinct(true)).getResultList();
        }


        return searchResult;
    }
}
