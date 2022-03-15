package com.car.rental.rent.user.repository;

import com.car.rental.rent.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserSearchRepository {
}
