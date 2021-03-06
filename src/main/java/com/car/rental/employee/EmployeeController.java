package com.car.rental.employee;

import com.car.rental.employee.dto.EmployeeAddDto;
import com.car.rental.employee.dto.EmployeeDto;
import com.car.rental.employee.dto.EmployeeSearchDto;
import com.car.rental.employee.dto.EmployeeUpdateDto;
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
@RequestMapping("/api/employee")
public class EmployeeController {

    public static final Logger LOGGER = Logger.getLogger(EmployeeController.class.getName());
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        LOGGER.info("CarController(" + employeeService + ")");
        this.employeeService = employeeService;
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody EmployeeAddDto dto) {
        LOGGER.info("PostMapping create(" + dto + ")");
        Employee employee = employeeService.addEmployee(dto);
        return ResponseEntity.created(URI.create(Config.APPLICATION_PATH + employee.getId())).build();
    }

    @GetMapping()
    public ResponseEntity<Page<EmployeeDto>> getAll(
            @PageableDefault(page = 0, size = 6) @RequestBody Pageable pageable) {
        LOGGER.info("GetMapping getAll(" + pageable + ")");
        Page<EmployeeDto> employees = employeeService.getAll(pageable);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable Long id) {
        LOGGER.info("GetMapping findById(" + id + ")");
        EmployeeDto employeeDto = employeeService.findById(id);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping("/search")
    public ResponseEntity search(@PageableDefault(size = 6, page = 0) Pageable pageable,
                                 @RequestBody EmployeeSearchDto employeeSearchDto) {
        LOGGER.info("GetMapping search(" + pageable + ", " + employeeSearchDto + ")");
        Page<EmployeeDto> employeeDto = employeeService.search(pageable, employeeSearchDto);
        return ResponseEntity.ok(employeeDto);
    }

    @PatchMapping("/update")
    public ResponseEntity updateEmployee(@RequestBody EmployeeUpdateDto employeeUpdateDto) {
        LOGGER.info("PatchMapping updateEmployee(" + employeeUpdateDto + ")");
        if (employeeService.updateEmployee(employeeUpdateDto) != null) {
            LOGGER.info("Update success");
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Update failed");
        return ResponseEntity.badRequest().body("Something gone wrong with update!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Long id) {
        LOGGER.info("DeleteMapping deleteEmployee(" + id + ")");
        if (employeeService.deleteEmployee(id)) {
            return ResponseEntity.ok().body("Employee with id: " + id + " successfully deleted");
        }
        return ResponseEntity.badRequest().body("Employee with given ID not exist");
    }
}
