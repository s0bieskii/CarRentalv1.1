package com.car.rental.report;

import com.car.rental.car.repository.CarRepository;
import com.car.rental.employee.repository.EmployeeRepository;
import com.car.rental.report.dto.ReturnReportAddDto;
import com.car.rental.report.dto.ReturnReportDto;
import com.car.rental.report.dto.ReturnReportSearchDto;
import com.car.rental.report.dto.ReturnReportUpdateDto;
import com.car.rental.report.mapper.ReturnReportMapper;
import com.car.rental.report.repository.ReturnReportRepository;
import com.car.rental.utils.PageWrapper;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReturnReportService {

    public static final Logger LOGGER = Logger.getLogger(ReturnReportService.class.getName());
    private final ReturnReportRepository returnReportRepository;
    private final ReturnReportMapper returnReportMapper;
    private final CarRepository carRepository;
    private final EmployeeRepository employeeRepository;

    public ReturnReportService(ReturnReportRepository returnReportRepository, ReturnReportMapper returnReportMapper,
                               CarRepository carRepository, EmployeeRepository employeeRepository) {
        LOGGER.info("Creating ReturnReportService with " + returnReportRepository + ", " + returnReportMapper + ", "
                + returnReportRepository + ", " + returnReportMapper);
        this.returnReportRepository = returnReportRepository;
        this.returnReportMapper = returnReportMapper;
        this.carRepository = carRepository;
        this.employeeRepository = employeeRepository;
    }

    public ReturnReport addReturnReport(ReturnReportAddDto returnDto) {
        LOGGER.info("addReturnReport(" + returnDto + ")");
        if (returnDto.getCarId() != null && carRepository.existsById(returnDto.getCarId()) &&
                returnDto.getEmployeeId() != null && employeeRepository.existsById(returnDto.getEmployeeId())) {
            ReturnReport report = returnReportMapper.returnReportAddDtoToReturnReport(returnDto);
            report.setCar(carRepository.getById(returnDto.getCarId()));
            report.setEmployee(employeeRepository.getById(returnDto.getEmployeeId()));
            return returnReportRepository.save(report);
        }
        return null;
    }

    public Page<ReturnReportDto> getAll(Pageable pageable) {
        LOGGER.info("getAll(" + pageable);
        Page<ReturnReportDto> reportsDto =
                returnReportRepository.findAll(pageable).map(returnReportMapper::returnReportToReturnReportDto);
        LOGGER.info("All founded reports: " + reportsDto.getTotalElements());
        return reportsDto;
    }

    public ReturnReportDto findById(Long id) {
        LOGGER.info("findById(" + id + ")");
        return returnReportRepository.findById(id).map(returnReportMapper::returnReportToReturnReportDto)
                .orElse(null);
    }

    public Page search(Pageable pageable, ReturnReportSearchDto reportSearchDto) {
        LOGGER.info("search(" + pageable + ", " + reportSearchDto + ")");
        List<ReturnReportDto> returnReportDto = returnReportRepository.find(reportSearchDto).stream()
                .map(returnReportMapper::returnReportToReturnReportDto).collect(Collectors.toList());
        LOGGER.info("Found: " + returnReportDto.size());
        return PageWrapper.listToPage(pageable, returnReportDto);
    }

    public ReturnReportDto updateReturnReport(ReturnReportUpdateDto rentalDto) {
        LOGGER.info("updateReturnReport(" + rentalDto + ")");
        if (rentalDto.getId() != null && returnReportRepository.existsById(rentalDto.getId())) {
            ReturnReport report = returnReportMapper.returnReportUpdateToReturnReport(rentalDto);
            if ((rentalDto.getCarId() != null && !carRepository.existsById(rentalDto.getCarId())) ||
                    (rentalDto.getEmployeeId() != null && !employeeRepository.existsById(rentalDto.getEmployeeId()))) {
                return null;
            }
            if (rentalDto.getCarId() != null) {
                report.setCar(carRepository.getById(rentalDto.getCarId()));
            }
            if (rentalDto.getEmployeeId() != null) {
                report.setEmployee(employeeRepository.getById(rentalDto.getEmployeeId()));
            }
            returnReportRepository.save(report);
            LOGGER.info("Return Report successfully updated");
            return returnReportMapper.returnReportToReturnReportDto(report);
        }
        LOGGER.info("Return Report update failed!");
        return null;
    }

    public boolean deleteReturnReport(Long id) {
        if (returnReportRepository.existsById(id)) {
            ReturnReport report = returnReportRepository.getById(id);
            report.setDeleted(true);
            returnReportRepository.save(report);
            return true;
        }
        return false;
    }
}
