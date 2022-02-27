package com.car.rental.user.mapper;

import com.car.rental.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper USER_DTO_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(UserDto userDto);
}
