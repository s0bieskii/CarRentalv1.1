package com.car.rental.car.repository;

import com.car.rental.car.Car;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>, CarSearchRepository {

    Page<Car> findAll(Pageable pageable);

    @Query(value = "SELECT distinct brand FROM car WHERE brand IS NOT NULL", nativeQuery = true)
    List<String> getAllCarsBrands();

    @Query(value = "SELECT distinct model FROM car WHERE model IS NOT NULL", nativeQuery = true)
    List<String> getAllCarsModels();
}
