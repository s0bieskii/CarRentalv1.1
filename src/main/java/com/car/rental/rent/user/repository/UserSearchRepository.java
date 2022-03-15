package com.car.rental.rent.user.repository;

import com.car.rental.rent.user.User;
import com.car.rental.rent.user.dto.UserSearchDto;
import java.util.List;

public interface UserSearchRepository {

    List<User> find(UserSearchDto userSearchDto);
}
