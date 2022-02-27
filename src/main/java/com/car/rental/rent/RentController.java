package com.car.rental.rent;

import com.car.rental.rent.dto.RentAddDto;
import com.car.rental.rent.dto.RentDto;
import com.car.rental.rent.dto.RentSearchDto;
import com.car.rental.rent.dto.RentUpdateDto;
import com.car.rental.utils.Config;
import java.net.URI;
import java.util.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rents")
public class RentController {

    public static final Logger LOGGER = Logger.getLogger(RentController.class.getName());
    private final RentService rentService;

    public RentController(RentService rentController) {
        LOGGER.info("RentController(" + rentController + ")");
        this.rentService = rentController;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody RentAddDto dto) {
        LOGGER.info("PostMapping create(" + dto + ")");
        Rent rent = rentService.addRent(dto);
        if(rent == null){
            return ResponseEntity.badRequest().body("Something gone wrong!");
        }
        return ResponseEntity.created(URI.create(Config.applicationPath + rent.getId())).build();
    }

    @GetMapping
    public ResponseEntity<Page<RentDto>> getALl(
            @PageableDefault(size = 6, page = 0) Pageable pageable) {
        LOGGER.info("GetMapping getAll(" + pageable + ")");
        Page<RentDto> rent = rentService.getAll(pageable);
        return ResponseEntity.ok(rent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentDto> findById(@PathVariable Long id) {
        LOGGER.info("GetMapping findById(" + id + ")");
        RentDto rentDto = rentService.findById(id);
        return ResponseEntity.ok(rentDto);
    }

    @GetMapping("/find")
    public ResponseEntity search(@PageableDefault(page = 0, size = 6) Pageable pageable,
                                 @RequestBody RentSearchDto rentSearchDto) {
        LOGGER.info("GetMapping search(" + pageable + ", " + rentSearchDto + ")");
        Page<RentDto> rentDto = rentService.search(pageable, rentSearchDto);
        return ResponseEntity.ok(rentDto);
    }

    @PatchMapping("/update")
    public ResponseEntity updateRent(@RequestBody RentUpdateDto dto) {
        LOGGER.info("PatchMapping updateRent(" + dto + ")");
        if (rentService.updateRent(dto) != null) {
            LOGGER.info("Update success");
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Update failed");
        return ResponseEntity.badRequest().body("Something gone wrong with update!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRent(@PathVariable Long id) {
        LOGGER.info("DeleteMapping deleteRent(" + id + ")");
        if (rentService.deleteRent(id)) {
            return ResponseEntity.ok().body("Rent with id: " + id + " successfully deleted");
        }
        return ResponseEntity.badRequest().body("Rent with given ID not exist");
    }
}
