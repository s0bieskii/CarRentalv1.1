package com.car.rental.rent;

import com.car.rental.car.repository.CarRepository;
import com.car.rental.rent.dto.RentAddDto;
import com.car.rental.rent.dto.RentAdminViewDto;
import com.car.rental.rent.dto.RentDto;
import com.car.rental.rent.dto.RentSearchDto;
import com.car.rental.rent.dto.RentUpdateDto;
import com.car.rental.rent.mapper.RentMapper;
import com.car.rental.rent.repository.RentRepository;
import com.car.rental.report.ReturnReport;
import com.car.rental.user.User;
import com.car.rental.user.repository.UserRepository;
import com.car.rental.utils.Config;
import com.car.rental.utils.PageWrapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RentService {

    public static final Logger LOGGER = Logger.getLogger(RentService.class.getName());
    private final RentRepository rentRepository;
    private final RentMapper rentMapper;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public RentService(RentRepository rentRepository, RentMapper rentMapper, CarRepository carRepository,
                       UserRepository userRepository) {
        LOGGER.info("Creating RentService with(" + rentRepository + ", " + rentMapper);
        this.rentRepository = rentRepository;
        this.rentMapper = rentMapper;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    public Rent addRent(RentAddDto dto) {
        LOGGER.info("addRent(" + dto + ")");
        if (carRepository.existsById(dto.getCarId()) && userRepository.existsById(dto.getUserId())
                && dto.getStart() != null && dto.getEnd() != null &&
                checkRentIsPossibleInGivenData(dto.getCarId(), dto.getStart(), dto.getEnd())) {
            Rent rentToAdd = rentMapper.rentAddDtoToRent(dto);
            rentToAdd.setReport(new ReturnReport());
            rentToAdd.setCar(carRepository.getById(dto.getCarId()));
            User userToSet = userRepository.getById(dto.getUserId());
            rentToAdd.setUser(userToSet);
            rentToAdd.setFinalPrice(calculateRentFinalPrice(rentToAdd, dto.getStart(), dto.getEnd()));
            Rent savedRent = rentRepository.save(rentToAdd);
            return savedRent;
        }
        return null;
    }

    public Page<RentDto> getAll(Pageable pageable) {
        LOGGER.info("getAll(" + pageable);
        Page<RentDto> rentDto = rentRepository.findAll(pageable).map(rentMapper::rentToRentDto);
        LOGGER.info("All founded rents: " + rentDto.getTotalElements());
        return rentDto;
    }

    public RentDto findById(Long id) {
        LOGGER.info("findById(" + id + ")");
        RentDto rent = rentRepository.findById(id).map(rentMapper::rentToRentDto).orElse(null);
        return rent;
    }

    public RentAdminViewDto findByIdAdminView(Long id) {
        LOGGER.info("findById(" + id + ")");
        RentAdminViewDto rent = rentRepository.findById(id).map(rentMapper::rentToRentAdminViewDto).orElse(null);
        return rent;
    }

    public Page<RentDto> search(Pageable pageable, RentSearchDto rentSearchDto) {
        LOGGER.info("search(" + pageable + ", " + rentSearchDto + ")");
        List<RentDto> rentResultList = rentRepository.find(rentSearchDto).stream()
                .map(rentMapper::rentToRentDto).collect(Collectors.toList());
        LOGGER.info("Found: " + rentResultList);
        return PageWrapper.listToPage(pageable, rentResultList);
    }

    public RentDto updateRent(RentUpdateDto dto) {
        LOGGER.info("updateRent(" + dto + ")");
        if (dto.getId() != null && rentRepository.existsById(dto.getId())) {
            Rent rent = rentMapper.rentUpdateDtoToEmployee(dto);
            rentRepository.save(rent);
            LOGGER.info("Rent successfully updated");
            return rentMapper.rentToRentDto(rent);
        }
        LOGGER.info("Rent update failed!");
        return null;
    }

    public boolean deleteRent(Long id) {
        LOGGER.info("deleteRent(" + id + ")");
        if (rentRepository.existsById(id)) {
            LOGGER.info("Rent with given ID exists");
            Rent rent = rentRepository.getById(id);
            rent.setDeleted(true);
            rentRepository.save(rent);
            LOGGER.info("Successful deleted Rent with id=" + id);
            return true;
        }
        LOGGER.info("Rent with given id not exist");
        return false;
    }

    private boolean checkRentIsPossibleInGivenData(Long carId, LocalDateTime start, LocalDateTime end) {
        if (start != null && end != null && carRepository.existsById(carId)) {
            LocalDateTime startCopy = start.minusHours(Config.timeDelayUntilNextRent);
            LocalDateTime endCopy = end.plusHours(Config.timeDelayUntilNextRent);
            List<Rent> rents = rentRepository.findRentByCarId(carId);
            for (Rent rentCheck : rents) {
                LocalDateTime rentCheckStart = rentCheck.getStart();
                LocalDateTime rentCheckEnd = rentCheck.getEnd();
                if (rentCheck.isDeleted() == false &&
                        (rentCheckEnd.isAfter(startCopy) && rentCheckStart.isBefore(endCopy))) {
                    return false;
                }
            }
        }
        return true;
    }

    private BigDecimal calculateRentFinalPrice(Rent rent, LocalDateTime start, LocalDateTime end) {
        if (carRepository.existsById(rent.getCar().getId()) && start != null && end != null && start.isBefore(end)) {
            long age = ChronoUnit.YEARS.between(rent.getUser().getBirth(), LocalDate.now());
            BigDecimal pricePerDay = carRepository.getById(rent.getCar().getId()).getCarDetails().getPrice();
            long rentDays = ChronoUnit.DAYS.between(start, end);
            double discount = 0;
            if (age > 26) {
                discount = discount + 0.05;
            }
            if (rentDays > 7) {
                discount = discount + 0.03;
            }
            if (rentDays > 14) {
                discount = discount + 0.03;
            }
            if (discount > 0) {
                return BigDecimal.valueOf(
                        (pricePerDay.doubleValue() * rentDays) - ((pricePerDay.doubleValue() * rentDays) * discount));
            } else {
                return BigDecimal.valueOf(pricePerDay.doubleValue() * rentDays);
            }
        }
        return null;
    }
}
