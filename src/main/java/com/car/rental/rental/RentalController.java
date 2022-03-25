package com.car.rental.rental;

import com.car.rental.rental.dto.RentalAddDto;
import com.car.rental.rental.dto.RentalDto;
import com.car.rental.rental.dto.RentalSearchDto;
import com.car.rental.rental.dto.RentalUpdateDto;
import com.car.rental.rental.dto.RentalWithoutEmployeeDto;
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
@RequestMapping("/api/rentals")
public class RentalController {

    public static final Logger LOGGER = Logger.getLogger(RentalController.class.getName());
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        LOGGER.info("RentalController(" + rentalService + ")");
        this.rentalService = rentalService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody RentalAddDto rentalDto) {
        LOGGER.info("PostMapping create(" + rentalDto + ")");
        Rental rental = rentalService.addRental(rentalDto);
        return ResponseEntity.created(URI.create(Config.APPLICATION_PATH + rental.getId())).build();
    }

    @GetMapping
    public ResponseEntity<Page<RentalDto>> getAll(@PageableDefault(page = 0, size = 6) @RequestBody Pageable pageable) {
        LOGGER.info("GetMapping getAll(" + pageable + ")");
        Page<RentalDto> rentals = rentalService.getAll(pageable);
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDto> findById(@PathVariable Long id) {
        LOGGER.info("GetMapping findById(" + id + ")");
        RentalDto rentalDto = rentalService.findById(id);
        return ResponseEntity.ok(rentalDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RentalWithoutEmployeeDto>> search(@PageableDefault(page = 0, size = 6) Pageable pageable,
                                                                 @RequestBody RentalSearchDto rentalDto) {
        LOGGER.info("GetMapping search(" + pageable + ", " + rentalDto + ")");
        Page<RentalWithoutEmployeeDto> rentals = rentalService.search(pageable, rentalDto);
        return ResponseEntity.ok(rentals);
    }

    @PatchMapping("/update")
    public ResponseEntity updateRental(@RequestBody RentalUpdateDto rentalDto) {
        LOGGER.info("PatchMapping updateRental(" + rentalDto + ")");
        if (rentalService.updateRental(rentalDto) != null) {
            LOGGER.info("Update success");
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Update failed");
        return ResponseEntity.badRequest().body("Something gone wrong with update!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRental(@PathVariable Long id) {
        LOGGER.info("DeleteMapping deleteRental(" + id + ")");
        if (rentalService.deleteRental(id)) {
            return ResponseEntity.ok().body("Rental with id: " + id + " successfully deleted");
        }
        return null;
    }

}
