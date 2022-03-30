package com.car.rental.user;

import com.car.rental.user.dto.UserAddDto;
import com.car.rental.user.dto.UserDto;
import com.car.rental.user.dto.UserSearchDto;
import com.car.rental.user.dto.UserUpdateDto;
import com.car.rental.user.mapper.UserMapper;
import com.car.rental.user.repository.UserRepository;
import com.car.rental.utils.PageWrapper;
import com.car.rental.web.security.Role;
import com.car.rental.web.security.repository.RoleRepository;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper,
                       PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    public User addUser(UserAddDto userAddDto) {
        LOGGER.info("addUser(" + userAddDto + ")");
        User user = userMapper.userAddDtoToUser(userAddDto);
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRoles(Set.of(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
        if (userDto.getId() != null && userRepository.existsById(userDto.getId()) && userDto.getPassword() != null) {
            User user = userMapper.userUpdateDtoToUser(userDto);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            User savedUser = userRepository.save(user);
            LOGGER.info("User successfully updated");
            return userMapper.userToUserDto(savedUser);
        }
        LOGGER.info("User update failed!");
        return null;
    }

    public UserDto updateUserFix(UserUpdateDto userDto) {
        LOGGER.info("updateUser(" + userDto + ")");
        if (userDto.getId() != null && userRepository.existsById(userDto.getId())) {
            User user = userRepository.getById(userDto.getId());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setBirth(userDto.getBirth());
            user.setEmail(userDto.getEmail());
            if(userDto.getPassword()!=null){
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
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

    public UserDto getCurrentLoggedUser() {
        LOGGER.info("getCurrentLoggedUser()");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();
            User user = userRepository.findUserByEmail(email);
            return userMapper.userToUserDto(user);
        }
        return null;
    }
}
