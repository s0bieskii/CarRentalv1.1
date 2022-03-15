package com.car.rental.user.mapper;

import com.car.rental.rent.mapper.RentMapper;
import com.car.rental.user.dto.UserDto;
import com.car.rental.user.dto.UserUpdateDto;
import com.car.rental.user.User;
import com.car.rental.user.dto.UserAddDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {RentMapper.class})
public interface UserMapper {
    UserMapper USER_DTO_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);
    User userAddDtoToUser(UserAddDto userAddDto);
    User userUpdateDtoToUser(UserUpdateDto userUpdateDto);
}
