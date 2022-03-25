package com.car.rental.report;

import com.car.rental.report.dto.ReturnReportAddDto;
import com.car.rental.report.dto.ReturnReportDto;
import com.car.rental.report.dto.ReturnReportSearchDto;
import com.car.rental.report.dto.ReturnReportUpdateDto;
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
@RequestMapping("/api/reports")
public class ReturnReportController {

    public static final Logger LOGGER = Logger.getLogger(ReturnReportController.class.getName());
    private final ReturnReportService returnReportService;

    public ReturnReportController(ReturnReportService returnReportService) {
        LOGGER.info("Creating ReturnReportService with " + returnReportService);
        this.returnReportService = returnReportService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody ReturnReportAddDto returnDto) {
        LOGGER.info("PostMapping create(" + returnDto + ")");
        ReturnReport report = returnReportService.addReturnReport(returnDto);
        return ResponseEntity.created(URI.create(Config.APPLICATION_PATH + report.getId())).build();
    }

    @GetMapping
    public ResponseEntity<Page<ReturnReportDto>> getAll(
            @PageableDefault(page = 0, size = 6) @RequestBody Pageable pageable) {
        LOGGER.info("GetMapping getAll(" + pageable + ")");
        Page<ReturnReportDto> rentals = returnReportService.getAll(pageable);
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnReportDto> findById(@PathVariable Long id) {
        LOGGER.info("GetMapping findById(" + id + ")");
        ReturnReportDto returnReportDto = returnReportService.findById(id);
        return ResponseEntity.ok(returnReportDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ReturnReportDto>> search(@PageableDefault(page = 0, size = 6) Pageable pageable,
                                                        @RequestBody ReturnReportSearchDto reportSearchDto) {
        LOGGER.info("GetMapping search(" + pageable + ", " + reportSearchDto + ")");
        Page<ReturnReportDto> rentals = returnReportService.search(pageable, reportSearchDto);
        return ResponseEntity.ok(rentals);
    }

    @PatchMapping("/update")
    public ResponseEntity updateReport(@RequestBody ReturnReportUpdateDto rentalDto) {
        LOGGER.info("PatchMapping updateReport(" + rentalDto + ")");
        if (returnReportService.updateReturnReport(rentalDto) != null) {
            LOGGER.info("Update success");
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Update failed");
        return ResponseEntity.badRequest().body("Something gone wrong with update!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReport(@PathVariable Long id) {
        LOGGER.info("DeleteMapping deleteReport(" + id + ")");
        if (returnReportService.deleteReturnReport(id)) {
            return ResponseEntity.ok().body("Report with id: " + id + " successfully deleted");
        }
        return ResponseEntity.badRequest().body("Return report with given ID not exist");
    }
}
