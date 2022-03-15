package com.car.rental.rent.user.mapper;

import com.car.rental.rent.mapper.RentMapper;
import com.car.rental.rent.user.dto.UserDto;
import com.car.rental.rent.user.dto.UserUpdateDto;
import com.car.rental.rent.user.User;
import com.car.rental.rent.user.dto.UserAddDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {RentMapper.class})
public interface UserMapper {
    UserMapper USER_DTO_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);
    User userAddDtoToUser(UserAddDto userAddDto);
    User userUpdateDtoToUser(UserUpdateDto userUpdateDto);
}
