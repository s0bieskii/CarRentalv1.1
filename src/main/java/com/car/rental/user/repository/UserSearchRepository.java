package com.car.rental.user.repository;

import com.car.rental.user.User;
import com.car.rental.user.dto.UserSearchDto;
import java.util.List;

public interface UserSearchRepository {

    List<User> find(UserSearchDto userSearchDto);
}
