package com.car.rental.integration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.car.rental.report.ReturnReport;
import com.car.rental.report.ReturnReportService;
import com.car.rental.report.dto.ReturnReportAddDto;
import com.car.rental.report.dto.ReturnReportDto;
import com.car.rental.report.dto.ReturnReportSearchDto;
import com.car.rental.report.dto.ReturnReportUpdateDto;
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
public class ReturnReportIntegrationTest {

    private final ReturnReportService returnReportService;
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Autowired
    public ReturnReportIntegrationTest(ReturnReportService returnReportService, JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.returnReportService = returnReportService;
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
    void addReturnReportShouldReturnNullWhenGivenCarIdNotExistInDatabase(){
        //given
        ReturnReportAddDto reportAddDto = new ReturnReportAddDto();
        Long carId = Long.MAX_VALUE;
        Long employeeId = 1L;
        reportAddDto.setCarId(carId);
        reportAddDto.setEmployeeId(employeeId);
        //when
        ReturnReport isAdd = returnReportService.addReturnReport(reportAddDto);
        //then
        assertNull(isAdd);
    }

    @Test
    void addReturnReportShouldReturnNullWhenGivenEmployeeIdNotExistInDatabase(){
        //given
        ReturnReportAddDto reportAddDto = new ReturnReportAddDto();
        Long employeeId = Long.MAX_VALUE;
        Long carId = 1L;
        reportAddDto.setEmployeeId(employeeId);
        reportAddDto.setCarId(carId);
        //when
        ReturnReport isAdd = returnReportService.addReturnReport(reportAddDto);
        //then
        assertNull(isAdd);
    }

    @Test
    void addReturnReportShouldReturnInstanceOfReturnReportIfAddIsSuccessful(){
        //given
        ReturnReportAddDto reportAddDto = new ReturnReportAddDto();
        Long carId = 1L;
        Long employeeId = 1L;
        reportAddDto.setCarId(carId);
        reportAddDto.setEmployeeId(employeeId);
        //when
        ReturnReport isAdd = returnReportService.addReturnReport(reportAddDto);
        //then
        assertInstanceOf(ReturnReport.class, isAdd);
    }

    @Test
    void addReturnReportShouldReturnReturnReportWithAssignedCarAndEmployeeField(){
        //given
        ReturnReportAddDto reportAddDto = new ReturnReportAddDto();
        Long carId = 1L;
        Long employeeId = 1L;
        reportAddDto.setCarId(carId);
        reportAddDto.setEmployeeId(employeeId);
        //when
        ReturnReport isAdd = returnReportService.addReturnReport(reportAddDto);
        //then
        assertAll(
                () -> assertNotNull(isAdd.getCar()),
                () -> assertNotNull(isAdd.getEmployee())
        );
    }

    @Test
    void addReturnReportShouldReturnReturnReportWithAutoAssignedDeleteFieldToFalse(){
        //given
        ReturnReportAddDto reportAddDto = new ReturnReportAddDto();
        Long carId = 1L;
        Long employeeId = 1L;
        reportAddDto.setCarId(carId);
        reportAddDto.setEmployeeId(employeeId);
        //when
        ReturnReport isAdd = returnReportService.addReturnReport(reportAddDto);
        //then
        assertFalse(isAdd.isDamaged());
    }

    @Test
    void addReturnReportShouldAddAutomaticallyId(){
        //given
        ReturnReportAddDto reportAddDto = new ReturnReportAddDto();
        Long carId = 1L;
        Long employeeId = 1L;
        reportAddDto.setCarId(carId);
        reportAddDto.setEmployeeId(employeeId);
        //when
        ReturnReport isAdd = returnReportService.addReturnReport(reportAddDto);
        //then
        assertNotNull(isAdd.getId());
    }

    @Test
    void getAllShouldReturnAllReturnReportsInDatabase(){
        //given
        PageRequest page = PageRequest.of(0, 6);
        int expectingQuantity = 13;
        //when
        Page<ReturnReportDto> reports = returnReportService.getAll(page);
        //then
        assertEquals(expectingQuantity, reports.getTotalElements());
    }

    @Test
    void getAllShouldReturnPageWithGivenPageAndSize(){
        //given
        PageRequest page = PageRequest.of(0, 6);
        //when
        Page<ReturnReportDto> reports = returnReportService.getAll(page);
        //then
        assertAll(
                () -> assertEquals(0, reports.getPageable().getPageNumber()),
                () -> assertEquals(6, reports.getPageable().getPageSize())
        );
    }

    @Test
    void getAllShouldReturnEmptyPageWhenDatabaseIsEmpty(){
        //given
        PageRequest page = PageRequest.of(0, 6);
        //when
        cleanDb();
        Page<ReturnReportDto> reports = returnReportService.getAll(page);
        //then
        assertAll(
                () -> assertEquals(0, reports.getPageable().getPageNumber()),
                () -> assertEquals(6, reports.getPageable().getPageSize())
        );
        prepareDb();
    }

    @Test
    void findByIdShouldReturnNullIfReportWithGivenIdNotExist(){
        //given
        Long id = Long.MAX_VALUE;
        //when
        ReturnReportDto report = returnReportService.findById(id);
        //then
        assertNull(report);
    }

    @Test
    void findByIdShouldReturnInstanceOfReturnReportDtoIfReportWithGivenIdExist(){
        //given
        Long id = 1L;
        //when
        ReturnReportDto report = returnReportService.findById(id);
        //then
        assertAll(
                () -> assertNotNull(report),
                () -> assertInstanceOf(ReturnReportDto.class, report)
        );
    }

    @Test
    void findByIdShouldReturnReportWitheTheSameGivenId(){
        //given
        Long id = 1L;
        //when
        ReturnReportDto report = returnReportService.findById(id);
        //then
        assertEquals(id, report.getId());
    }

    @Test
    void searchMethodShouldReturnReturnPageWithOnlyOneReturnReportsWithTheSameIdLikeGiven(){
        //given
        PageRequest page = PageRequest.of(0, 6);
        ReturnReportSearchDto searchDto = new ReturnReportSearchDto();
        Long id = 1L;
        searchDto.setId(id);
        //when
        Page<ReturnReportDto> report = returnReportService.search(page, searchDto);
        //then
       assertAll(
               () -> assertEquals(1, report.getTotalElements()),
               () -> assertEquals(id, report.getContent().get(0).getId())
       );
    }

    @Test
    void searchMethodShouldReturnReturnPageWithReportsMatchingToGivenDamaged(){
        //given
        PageRequest page = PageRequest.of(0, 6);
        ReturnReportSearchDto searchDto = new ReturnReportSearchDto();
        boolean damaged = true;
        searchDto.setDamaged(damaged);
        int expectingQuantity = 2;
        //when
        Page<ReturnReportDto> report = returnReportService.search(page, searchDto);
        //then
        assertAll(
                () -> assertEquals(expectingQuantity, report.getTotalElements()),
                () -> assertEquals(damaged, report.getContent().get(0).getDamaged())
        );
    }

    @Test
    void searchMethodShouldReturnReturnPageWithReportsMatchingToGivenCarId(){
        //given
        PageRequest page = PageRequest.of(0, 6);
        ReturnReportSearchDto searchDto = new ReturnReportSearchDto();
        Long carId = 1L;
        searchDto.setCarId(carId);
        int expectingQuantity = 4;
        //when
        Page<ReturnReportDto> report = returnReportService.search(page, searchDto);
        //then
        assertAll(
                () -> assertEquals(expectingQuantity, report.getTotalElements()),
                () -> assertEquals(carId, report.getContent().get(0).getCar().getId())
        );
    }

    @Test
    void searchMethodShouldReturnReturnPageWithReportsMatchingToGivenEmployeeId(){
        //given
        PageRequest page = PageRequest.of(0, 6);
        ReturnReportSearchDto searchDto = new ReturnReportSearchDto();
        Long employeeId = 3L;
        searchDto.setEmployeeId(employeeId);
        int expectingQuantity = 3;
        //when
        Page<ReturnReportDto> report = returnReportService.search(page, searchDto);
        //then
        assertAll(
                () -> assertEquals(expectingQuantity, report.getTotalElements()),
                () -> assertEquals(employeeId, report.getContent().get(0).getEmployee().getId())
        );
    }

    @Test
    void searchMethodShouldReturnAllReturnReportsFromDatabaseWhenGivenReturnReportSearchDtoIsEmpty(){
        //given
        PageRequest page = PageRequest.of(0, 6);
        ReturnReportSearchDto searchDto = new ReturnReportSearchDto();
        int expectingQuantity = 13;
        //when
        Page<ReturnReportDto> report = returnReportService.search(page, searchDto);
        //then
        assertEquals(expectingQuantity, report.getTotalElements());
    }

    @Test
    void updateReturnReportShouldReturnNullIfReturnReportIdIsNull(){
        //given
        ReturnReportUpdateDto updateDto = new ReturnReportUpdateDto();
        //when
        ReturnReportDto report = returnReportService.updateReturnReport(updateDto);
        //then
        assertNull(report);
    }

    @Test
    void updateReturnReportShouldReturnNullIfReturnReportWithGivenIdNotExist(){
        //given
        ReturnReportUpdateDto updateDto = new ReturnReportUpdateDto();
        updateDto.setId(Long.MAX_VALUE);
        //when
        ReturnReportDto report = returnReportService.updateReturnReport(updateDto);
        //then
        assertNull(report);
    }

    @Test
    void updateReturnReportShouldReturnReturnReportWithUpdatedNoteField(){
        //given
        ReturnReportUpdateDto updateDto = new ReturnReportUpdateDto();
        updateDto.setId(1L);
        updateDto.setNote("Note after update");
        String noteBefore = returnReportService.findById(1L).getNote();
        //when
        ReturnReportDto afterUpdate = returnReportService.updateReturnReport(updateDto);
        //then
        assertAll(
                () -> assertNotEquals(noteBefore, afterUpdate.getNote()),
                () -> assertEquals("Note after update", afterUpdate.getNote())
        );
    }

    @Test
    void updateReturnReportShouldReturnReturnReportWithUpdatedDamagedField(){
        //given
        ReturnReportUpdateDto updateDto = new ReturnReportUpdateDto();
        updateDto.setId(1L);
        updateDto.setDamaged(true);
        boolean damageBefore = returnReportService.findById(1L).getDamaged();
        //when
        ReturnReportDto afterUpdate = returnReportService.updateReturnReport(updateDto);
        //then
        assertAll(
                () -> assertNotEquals(damageBefore, afterUpdate.getDamaged()),
                () -> assertTrue(afterUpdate.getDamaged())
        );
    }

    @Test
    void updateReturnReportShouldReturnReturnReportWithUpdatedCarField(){
        //given
        ReturnReportUpdateDto updateDto = new ReturnReportUpdateDto();
        updateDto.setId(1L);
        updateDto.setCarId(2L);
        Long carIdBeforeUpdate = returnReportService.findById(1L).getCar().getId();
        //when
        ReturnReportDto afterUpdate = returnReportService.updateReturnReport(updateDto);
        //then
        assertAll(
                () -> assertNotEquals(carIdBeforeUpdate, afterUpdate.getCar().getId()),
                () -> assertEquals(2L, afterUpdate.getCar().getId())
        );
    }

    @Test
    void updateReturnReportShouldReturnReturnNullWhenGivenCarIdNotExist(){
        //given
        ReturnReportUpdateDto updateDto = new ReturnReportUpdateDto();
        updateDto.setId(1L);
        updateDto.setCarId(Long.MAX_VALUE);
        //when
        ReturnReportDto afterUpdate = returnReportService.updateReturnReport(updateDto);
        //then
        assertNull(afterUpdate);
    }

    @Test
    void updateReturnReportShouldReturnReturnReportWithUpdatedEmployeeField(){
        //given
        ReturnReportUpdateDto updateDto = new ReturnReportUpdateDto();
        updateDto.setId(1L);
        updateDto.setEmployeeId(2L);
        Long employeeIdBeforeUpdate = returnReportService.findById(1L).getEmployee().getId();
        //when
        ReturnReportDto afterUpdate = returnReportService.updateReturnReport(updateDto);
        //then
        assertAll(
                () -> assertNotEquals(employeeIdBeforeUpdate, afterUpdate.getEmployee().getId()),
                () -> assertEquals(2L, afterUpdate.getEmployee().getId())
        );
    }

    @Test
    void updateReturnReportShouldReturnReturnNullWhenGivenEmployeeIdNotExist(){
        //given
        ReturnReportUpdateDto updateDto = new ReturnReportUpdateDto();
        updateDto.setId(1L);
        updateDto.setEmployeeId(Long.MAX_VALUE);
        //when
        ReturnReportDto afterUpdate = returnReportService.updateReturnReport(updateDto);
        //then
        assertNull(afterUpdate);
    }

    @Test
    void deleteReturnReportWithGivenNotExistedIdShouldReturnFalse(){
        //given
        Long id = Long.MAX_VALUE;
        //when
        boolean check = returnReportService.deleteReturnReport(id);
        //then
        assertFalse(check);
    }

    @Test
    void deleteReturnReportWithExistingIdShouldReturnTrue(){
        //given
        Long id = 1L;
        //when
        boolean check = returnReportService.deleteReturnReport(id);
        //then
        assertTrue(check);
    }

    @Test
    void deleteReturnReportShouldChangeFieldDeletedToTrueForReportWithGivenId(){
        //given
        Long id = 1L;
        boolean beforeDelete = returnReportService.findById(id).getDeleted();
        //when
        returnReportService.deleteReturnReport(id);
        ReturnReportDto afterDelete = returnReportService.findById(id);
        //then
       assertAll(
               () -> assertNotEquals(beforeDelete, afterDelete.getDeleted()),
               () -> assertTrue(afterDelete.getDeleted())
       );
    }

}
