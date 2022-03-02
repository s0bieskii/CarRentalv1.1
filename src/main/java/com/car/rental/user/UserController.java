package com.car.rental.user;

import com.car.rental.user.dto.UserAddDto;
import com.car.rental.user.dto.UserDto;
import com.car.rental.user.dto.UserSearchDto;
import com.car.rental.user.dto.UserUpdateDto;
import com.car.rental.utils.Config;
import java.net.URI;
import java.util.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    public static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private final UserService userService;

    public UserController(UserService userService) {
        LOGGER.info("UserController( " + userService + ")");
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody UserAddDto userDto) {
        LOGGER.info("PostMapping create(" + userDto + ")");
        User user = userService.addUser(userDto);
        return ResponseEntity.created(URI.create(Config.applicationPath + user.getId())).build();
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAll(@PageableDefault(page = 0, size = 6) @RequestBody Pageable pageable) {
        LOGGER.info("GetMapping getAll(" + pageable + ")");
        Page<UserDto> users = userService.getAll(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        LOGGER.info("GetMapping findById(" + id + ")");
        UserDto userDto = userService.findById(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserDto>> search(@PageableDefault(page = 0, size = 6) Pageable pageable,
                                                                 @RequestBody UserSearchDto userDto) {
        LOGGER.info("GetMapping search(" + pageable + ", " + userDto + ")");
        Page<UserDto> rentals = userService.search(pageable, userDto);
        return ResponseEntity.ok(rentals);
    }

    @PatchMapping("/update")
    public ResponseEntity updateUser(@RequestBody UserUpdateDto userDto) {
        LOGGER.info("PatchMapping updateUser(" + userDto + ")");
        if (userService.updateUser(userDto) != null) {
            LOGGER.info("Update success");
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Update failed");
        return ResponseEntity.badRequest().body("Something gone wrong with update!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        LOGGER.info("DeleteMapping deleteUser(" + id + ")");
        if (userService.deleteUser(id)) {
            return ResponseEntity.ok().body("User with id: " + id + " successfully deleted");
        }
        return null;
    }
}
