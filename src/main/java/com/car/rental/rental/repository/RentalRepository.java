package com.car.rental.rental.repository;

import com.car.rental.rental.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long>, RentalSearchRepository{
}
