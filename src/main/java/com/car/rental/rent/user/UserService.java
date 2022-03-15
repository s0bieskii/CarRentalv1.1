package com.car.rental.rent.user;

import com.car.rental.rent.user.dto.UserDto;
import com.car.rental.rent.user.dto.UserUpdateDto;
import com.car.rental.rent.user.mapper.UserMapper;
import com.car.rental.rent.user.repository.UserRepository;
import com.car.rental.rent.user.dto.UserAddDto;
import com.car.rental.rent.user.dto.UserSearchDto;
import com.car.rental.utils.PageWrapper;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        LOGGER.info("RentalService(" + userRepository + ", " + userMapper + ")");
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User addUser(UserAddDto userAddDto) {
        LOGGER.info("addUser(" + userAddDto + ")");
        User user = userMapper.userAddDtoToUser(userAddDto);
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public Page<UserDto> getAll(Pageable pageable) {
        LOGGER.info("getAll(" + pageable);
        Page<UserDto> userDto = userRepository.findAll(pageable)
                .map(userMapper::userToUserDto);
        LOGGER.info("All founded users: " + userDto.getTotalElements());
        return userDto;
    }

    public UserDto findById(Long id) {
        LOGGER.info("findById(" + id + ")");
        UserDto user = userRepository.findById(id)
                .map(userMapper::userToUserDto).orElse(null);
        return user;
    }

    public Page<UserDto> search(Pageable pageable, UserSearchDto userDto) {
        LOGGER.info("search(" + pageable + ", " + userDto + ")");
        List<UserDto> rentalDto = userRepository.find(userDto).stream()
                .map(userMapper::userToUserDto).collect(Collectors.toList());
        LOGGER.info("Found: " + rentalDto.size());
        return PageWrapper.listToPage(pageable, rentalDto);
    }

    public UserDto updateUser(UserUpdateDto userDto) {
        LOGGER.info("updateUser(" + userDto + ")");
        if (userDto.getId() != null && userRepository.existsById(userDto.getId())) {
            User user = userMapper.userUpdateDtoToUser(userDto);
            User savedUser = userRepository.save(user);
            LOGGER.info("User successfully updated");
            return userMapper.userToUserDto(savedUser);
        }
        LOGGER.info("User update failed!");
        return null;
    }

    public boolean deleteUser(Long id) {
        LOGGER.info("deleteUser(" + id + ")");
        userRepository.findById(id).ifPresentOrElse(user -> {
            user.setDeleted(true);
            userRepository.save(user);
            LOGGER.info("Successfully deleted User with ID: (" + id + ")");
        }, () -> {
            LOGGER.info("Failed delete User with ID: (" + id + ")");
        });
        return userRepository.findById(id).map(User::isDeleted).orElse(false);
    }
}
