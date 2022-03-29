package com.car.rental.integration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.car.rental.user.User;
import com.car.rental.user.UserService;
import com.car.rental.user.dto.UserAddDto;
import com.car.rental.user.dto.UserDto;
import com.car.rental.user.dto.UserSearchDto;
import com.car.rental.user.dto.UserUpdateDto;
import java.time.LocalDate;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@SpringBootTest
public class UserIntegrationTest {

    private final UserService userService;
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Autowired
    public UserIntegrationTest(UserService userService, JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.userService = userService;
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @BeforeEach
    void prepareDb() {
        cleanDb();
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false,
                "UTF-8", new ClassPathResource("data.sql"));
        resourceDatabasePopulator.execute(dataSource);
    }

    void cleanDb() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false,
                "UTF-8", new ClassPathResource("clearDatabase.sql"));
        resourceDatabasePopulator.execute(dataSource);
    }

    @Test
    void addUserShouldReturnUserInstance() {
        //given
        UserAddDto userDto = new UserAddDto();
        userDto.setFirstName("Name");
        userDto.setLastName("LastName");
        userDto.setPassword("password");
        //when
        User user = userService.addUser(userDto);
        //then
        assertAll(
                () -> assertNotNull(user),
                () -> assertInstanceOf(User.class, user)
        );
    }

    @Test
    void addUserShouldAutoIncrementId() {
        //given
        UserAddDto userDto = new UserAddDto();
        userDto.setFirstName("Name");
        userDto.setLastName("LastName");
        userDto.setPassword("password");
        //when
        User user = userService.addUser(userDto);
        //then
        assertNotNull(user.getId());
    }

    @Test
    void addUserShouldReturnUserWithTheSameGivenFields() {
        //given
        UserAddDto userDto = new UserAddDto();
        userDto.setFirstName("Name");
        userDto.setLastName("LastName");
        userDto.setEmail("email@email.com");
        userDto.setPassword("password");
        //when
        User user = userService.addUser(userDto);
        //then
        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals("Name", user.getFirstName()),
                () -> assertEquals("LastName", user.getLastName()),
                () -> assertEquals("email@email.com", user.getEmail())
        );
    }

    @Test
    void getAllShouldReturnPageWithAllUsersInDatabase() {
        //given
        PageRequest page = PageRequest.of(0, 11);
        int expectingQuantity = 11;
        //when
        Page<UserDto> users = userService.getAll(page);
        //then
        assertEquals(expectingQuantity, users.getTotalElements());
    }

    @Test
    void getAllShouldReturnPageWithUserDto() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        //when
        Page<UserDto> users = userService.getAll(page);
        //then
        assertInstanceOf(UserDto.class, users.getContent().get(0));
    }

    @Test
    void getAllShouldReturnPageWithGivenSizeAndPageValue() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        //when
        Page<UserDto> users = userService.getAll(page);
        //then
        assertAll(
                () -> assertEquals(0, users.getPageable().getPageNumber()),
                () -> assertEquals(6, users.getPageable().getPageSize())
        );
    }

    @Test
    void findByIdShouldReturnNullIfUserWithGivenIdNotExist() {
        //given
        Long id = Long.MAX_VALUE;
        //when
        UserDto user = userService.findById(id);
        //then
        assertNull(user);
    }

    @Test
    void findByIdShouldReturnUserIfExistWithTheSameId() {
        //given
        Long id = 1L;
        //when
        UserDto user = userService.findById(id);
        //then
        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals(id, user.getId())
        );
    }

    @Test
    void searchShouldReturnUserDtoWithMatchingIdValue() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        Long id = 1L;
        UserSearchDto userDto = new UserSearchDto();
        userDto.setId(id);
        int expectingQuantity = 1;
        //when
        Page<UserDto> users = userService.search(page, userDto);
        //then
        assertAll(
                () -> assertEquals(expectingQuantity, users.getTotalElements()),
                () -> assertEquals(id, users.getContent().get(0).getId())
        );
    }

    @Test
    void searchShouldReturnEmptyPageIfAnyIdMatch() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        Long id = Long.MAX_VALUE;
        UserSearchDto userDto = new UserSearchDto();
        userDto.setId(id);
        int expectingQuantity = 0;
        //when
        Page<UserDto> users = userService.search(page, userDto);
        //then
        assertEquals(expectingQuantity, users.getTotalElements());
    }

    @Test
    void searchShouldReturnUserDtoWithMatchingFirstNameAndLastNameValue() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        UserSearchDto userDto = new UserSearchDto();
        userDto.setFirstName("user1");
        userDto.setLastName("user1");
        int expectingQuantity = 1;
        //when
        Page<UserDto> users = userService.search(page, userDto);
        //then
        assertAll(
                () -> assertEquals(expectingQuantity, users.getTotalElements()),
                () -> assertEquals("user1", users.getContent().get(0).getFirstName()),
                () -> assertEquals("user1", users.getContent().get(0).getLastName())
        );
    }

    @Test
    void searchShouldReturnEmptyPageWhenAnyFirstNameOrLastNameMatch() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        UserSearchDto userDto = new UserSearchDto();
        userDto.setFirstName("Peneloasdpa");
        userDto.setLastName("Czoasdpa");
        int expectingQuantity = 0;
        //when
        Page<UserDto> users = userService.search(page, userDto);
        //then
        assertEquals(expectingQuantity, users.getTotalElements());
    }

    @Test
    void searchShouldReturnUserDtoWithMatchingBirthField() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        UserSearchDto userDto = new UserSearchDto();
        LocalDate birth = LocalDate.of(1996, 6, 12);
        userDto.setBirth(birth);
        int expectingQuantity = 4;
        //when
        Page<UserDto> users = userService.search(page, userDto);
        //then
        assertAll(
                () -> assertEquals(expectingQuantity, users.getTotalElements()),
                () -> assertEquals(birth, users.getContent().get(0).getBirth())
        );
    }

    @Test
    void searchShouldReturnEmptyPageWhenAnyDateBirthNotMatch() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        UserSearchDto userDto = new UserSearchDto();
        LocalDate birth = LocalDate.of(1111, 6, 12);
        userDto.setBirth(birth);
        int expectingQuantity = 0;
        //when
        Page<UserDto> users = userService.search(page, userDto);
        //then
        assertEquals(expectingQuantity, users.getTotalElements());
    }

    @Test
    void searchShouldReturnUserDtoWithMatchingEmail() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        UserSearchDto userDto = new UserSearchDto();
        String email = "user1@user.pl";
        userDto.setEmail(email);
        int expectingQuantity = 1;
        //when
        Page<UserDto> users = userService.search(page, userDto);
        //then
        assertAll(
                () -> assertEquals(expectingQuantity, users.getTotalElements()),
                () -> assertEquals(email, users.getContent().get(0).getEmail())
        );
    }

    @Test
    void searchShouldReturnEmptyPageWhenAnyEmailMatch() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        UserSearchDto userDto = new UserSearchDto();
        String email = "emdasdail@email.pl";
        userDto.setEmail(email);
        int expectingQuantity = 0;
        //when
        Page<UserDto> users = userService.search(page, userDto);
        //then
        assertEquals(expectingQuantity, users.getTotalElements());
    }

    @Test
    void searchShouldReturnUserDtoWithMatchingActivatedField() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        UserSearchDto userDto = new UserSearchDto();
        boolean activated = true;
        userDto.setActivated(activated);
        int expectingQuantity = 10;
        //when
        Page<UserDto> users = userService.search(page, userDto);
        //then
        assertEquals(expectingQuantity, users.getTotalElements());
    }

    @Test
    void searchUserShouldReturnAllUsersFromDatabaseWhenAnyParamIsSet() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        UserSearchDto userDto = new UserSearchDto();
        int expectingQuantity = 11;
        //when
        Page<UserDto> updatedUser = userService.search(page, userDto);
        //then
        assertEquals(expectingQuantity, updatedUser.getTotalElements());
    }

    @Test
    void updateUserShouldReturnNullIfIdIsNotGiven() {
        //given
        UserUpdateDto userDto = new UserUpdateDto();
        //when
        UserDto updatedUser = userService.updateUser(userDto);
        //then
        assertNull(updatedUser);
    }

    @Test
    void updateUserShouldReturnNullIfUserWithGivenIdNotExist() {
        //given
        UserUpdateDto userDto = new UserUpdateDto();
        Long id = Long.MAX_VALUE;
        userDto.setId(id);
        //when
        UserDto updatedUser = userService.updateUser(userDto);
        //then
        assertNull(updatedUser);
    }

    @Test
    void updateUserShouldReturnUserWithUpdatedFieldsWhenUpdateIsSuccessful() {
        //given
        UserUpdateDto userDto = new UserUpdateDto();
        Long id = 12L;
        String firstNameAfter = "NewName";
        String lastNameAfter = "NewLastName";
        LocalDate birthAfter = LocalDate.of(6666, 4, 12);
        String emailAfter = "NewEmail@mail.com";
        String passwordAfter = "passwordNew";

        userDto.setId(id);
        userDto.setFirstName(firstNameAfter);
        userDto.setLastName(lastNameAfter);
        userDto.setBirth(birthAfter);
        userDto.setEmail(emailAfter);
        userDto.setPassword(passwordAfter);

        UserDto userBefore = userService.findById(id);

        //when
        UserDto userUpdated = userService.updateUser(userDto);
        //then
        assertAll(
                () -> assertNotEquals(userBefore.getFirstName(), firstNameAfter),
                () -> assertNotEquals(userBefore.getLastName(), lastNameAfter),
                () -> assertNotEquals(userBefore.getBirth(), birthAfter),
                () -> assertNotEquals(userBefore.getEmail(), emailAfter),
                () -> assertNotEquals(userBefore.getPassword(), passwordAfter),
                () -> assertEquals(userUpdated.getFirstName(), firstNameAfter),
                () -> assertEquals(userUpdated.getLastName(), lastNameAfter),
                () -> assertEquals(userUpdated.getBirth(), birthAfter),
                () -> assertEquals(userUpdated.getEmail(), emailAfter),
                () -> assertEquals(userUpdated.getPassword(), passwordAfter)
        );
    }

    @Test
    void deleteUserShouldReturnFalseIfUserWithGivenIdNotExist() {
        //given
        Long id = Long.MAX_VALUE;
        //when
        boolean isDeleted = userService.deleteUser(id);
        //then
        assertFalse(isDeleted);
    }

    @Test
    void deleteUserShouldReturnTrueIfUserWithGivenIdExist() {
        //given
        Long id = 1L;
        //when
        boolean isDeleted = userService.deleteUser(id);
        //then
        assertTrue(isDeleted);
    }

}
