package com.car.rental.rental;

import com.car.rental.rental.dto.RentalAddDto;
import com.car.rental.rental.dto.RentalDto;
import com.car.rental.rental.dto.RentalSearchDto;
import com.car.rental.rental.dto.RentalUpdateDto;
import com.car.rental.rental.dto.RentalWithoutEmployeeDto;
import com.car.rental.rental.mapper.RentalMapper;
import com.car.rental.rental.repository.RentalRepository;
import com.car.rental.utils.PageWrapper;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

    public static final Logger LOGGER = Logger.getLogger(RentalService.class.getName());
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;

    public RentalService(RentalRepository rentalRepository, RentalMapper rentalMapper) {
        LOGGER.info("RentalService(" + rentalRepository + ", " + rentalMapper + ")");
        this.rentalRepository = rentalRepository;
        this.rentalMapper = rentalMapper;
    }

    public Rental addRental(RentalAddDto rentalDto) {
        LOGGER.info("addRental(" + rentalDto + ")");
        Rental rental = rentalMapper.rentalAddDtoToRental(rentalDto);
        Rental savedRental = rentalRepository.save(rental);
        return savedRental;
    }

    public Page<RentalDto> getAll(Pageable pageable) {
        LOGGER.info("getAll(" + pageable);
        Page<RentalDto> rentalDto = rentalRepository.findAll(pageable)
                .map(rentalMapper::rentalToRentalDto);
        LOGGER.info("All founded rentals: " + rentalDto);
        return rentalDto;
    }

    public RentalDto findById(Long id) {
        LOGGER.info("findById(" + id + ")");
        RentalDto rental = rentalRepository.findById(id)
                .map(rentalMapper::rentalToRentalDto).orElse(null);
        return rental;
    }

    public Page<RentalWithoutEmployeeDto> search(Pageable pageable, RentalSearchDto rentalSearchDto) {
        LOGGER.info("search(" + pageable + ", " + rentalSearchDto + ")");
        List<RentalWithoutEmployeeDto> rentalDto = rentalRepository.find(rentalSearchDto).stream()
                .map(rentalMapper::rentalToRentalWithoutEmployeeDto).collect(Collectors.toList());
        LOGGER.info("Found: " + rentalDto.size());
        return PageWrapper.listToPage(pageable, rentalDto);
    }

    public RentalDto updateRental(RentalUpdateDto rentalDto) {
        LOGGER.info("updateRental(" + rentalDto + ")");
        if (rentalDto.getId() != null && rentalRepository.existsById(rentalDto.getId())) {
            Rental rental = rentalMapper.rentalUpdateDtoToRental(rentalDto);
            rentalRepository.save(rental);
            LOGGER.info("Rental successfully updated");
            return rentalMapper.rentalToRentalDto(rental);
        }
        LOGGER.info("Rental update failed!");
        return null;
    }

    public boolean deleteRental(Long id) {
        LOGGER.info("deleteRental(" + id + ")");
        rentalRepository.findById(id).ifPresentOrElse(rental -> {
            rental.setDeleted(true);
            rentalRepository.save(rental);
            LOGGER.info("Successfully deleted Rental with ID: (" + id + ")");
        }, () -> {
            LOGGER.info("Failed delete Rental with ID: (" + id + ")");
        });
        return rentalRepository.findById(id).map(Rental::isDeleted).orElse(false);
    }

}
