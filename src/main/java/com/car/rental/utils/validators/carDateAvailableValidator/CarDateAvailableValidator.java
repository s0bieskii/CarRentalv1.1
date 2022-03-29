package com.car.rental.utils.validators.carDateAvailableValidator;

import com.car.rental.car.dto.CarSearchDto;
import com.car.rental.car.repository.CarRepository;
import com.car.rental.rent.Rent;
import com.car.rental.rent.repository.RentRepository;
import com.car.rental.utils.Config;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CarDateAvailableValidator implements ConstraintValidator<CarDateAvailableValid, CarSearchDto> {

    final private RentRepository rentRepository;
    final private CarRepository carRepository;


    public CarDateAvailableValidator(RentRepository rentRepository,
                                     CarRepository carRepository) {
        this.rentRepository = rentRepository;
        this.carRepository = carRepository;
    }

    @Override
    public boolean isValid(CarSearchDto carSearch, ConstraintValidatorContext context) {

        if (carSearch.getStart() != null && carSearch.getEnd() != null && carSearch.getId() != null) {
            LocalDateTime start = carSearch.getStart().minusHours(Config.TIME_DELAY_UNTIL_NEXT_RENT);
            LocalDateTime end = carSearch.getEnd().plusHours(Config.TIME_DELAY_UNTIL_NEXT_RENT);
            List<Rent> rents = rentRepository.findRentForNotReturnedCarsByCarId(carSearch.getId());
            for (Rent rentCheck : rents) {
                LocalDateTime rentCheckStart = rentCheck.getStartDate();
                LocalDateTime rentCheckEnd = rentCheck.getEndDate();
                if (rentCheck.isDeleted() == false &&
                        (rentCheckEnd.isAfter(start) && rentCheckStart.isBefore(end))) {
                    return false;
                }
            }
        }
        return true;
    }
}
