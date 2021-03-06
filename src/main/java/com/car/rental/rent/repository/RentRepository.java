package com.car.rental.rent.repository;

import com.car.rental.rent.Rent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RentRepository extends JpaRepository<Rent, Long>, RentSearchRepository {

    @Query(value = "SELECT * FROM rents WHERE car_id = :id", nativeQuery = true)
    List<Rent> findRentByCarId(@Param("id") Long id);

    @Query(value = "SELECT * FROM rents WHERE user_id = :id", nativeQuery = true)
    List<Rent> findRentsByUserId(@Param("id") Long id);

    @Query(value = "SELECT * FROM rents WHERE returned = false AND car_id = :id", nativeQuery = true)
    List<Rent> findRentForNotReturnedCarsByCarId(@Param("id") Long id);
}
