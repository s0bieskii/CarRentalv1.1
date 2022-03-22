package com.car.rental.rental.repository;

import com.car.rental.rental.Rental;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long>, RentalSearchRepository {

    List<Rental> findAll();

    @Query(value = "SELECT * FROM rental LEFT JOIN rental_cars ON rental.id = rental_cars.rental_id where cars_id = :carId", nativeQuery = true)
    Optional<Rental> findRentalByCarId(@Param("carId") Long carId);
}
