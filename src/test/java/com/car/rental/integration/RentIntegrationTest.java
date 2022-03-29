package com.car.rental.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.car.rental.rent.Rent;
import com.car.rental.rent.RentService;
import com.car.rental.rent.dto.RentAddDto;
import com.car.rental.rent.dto.RentAdminViewDto;
import com.car.rental.rent.dto.RentDto;
import com.car.rental.rent.dto.RentSearchDto;
import com.car.rental.rent.dto.RentUpdateDto;
import com.car.rental.utils.Config;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class RentIntegrationTest {

    private final RentService rentService;
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Autowired
    public RentIntegrationTest(RentService rentService, JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.rentService = rentService;
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
    void addRentSuccessfulShouldReturnInstanceOfRent() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(2L);
        dto.setUserId(1L);
        dto.setStartDate(LocalDateTime.of(2025, 1, 10, 12, 30));
        dto.setEndDate(LocalDateTime.of(2025, 1, 15, 12, 30));
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertAll(
                () -> assertInstanceOf(Rent.class, rent),
                () -> assertNotNull(rent)
        );

    }

    @Test
    void addRentFailedShouldReturnNull() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(2L);
        dto.setUserId(1L);
        dto.setStartDate(LocalDateTime.of(2023, 1, 10, 12, 30));
        dto.setEndDate(LocalDateTime.of(2023, 1, 15, 12, 30));
        //when
        rentService.addRent(dto);
        Rent secondRent = rentService.addRent(dto);
        //then
        assertNull(secondRent);
    }

    @Test
    void addRentWithGivenDataRangeEqualToExistingRentShouldFailed() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(5L);
        dto.setUserId(1L);
        dto.setStartDate(LocalDateTime.of(2022, 2, 10, 12, 30));
        dto.setEndDate(LocalDateTime.of(2022, 2, 15, 12, 30));
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertNull(rent);
    }

    @Test
    void addRentWithGivenDataRangeStartBeforeExistingRentStartAndGivenEndEqualToExistingRentEndShouldFailed() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(5L);
        dto.setUserId(1L);
        dto.setStartDate(LocalDateTime.of(2022, 2, 5, 12, 30));
        dto.setEndDate(LocalDateTime.of(2022, 2, 15, 12, 30));
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertNull(rent);
    }

    @Test
    void addRentWithGivenDataRangeStartEqualExistingRentStartAndGivenEndAfterToExistingRentEndShouldFailed() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(5L);
        dto.setUserId(1L);
        dto.setStartDate(LocalDateTime.of(2022, 2, 10, 12, 30));
        dto.setEndDate(LocalDateTime.of(2022, 2, 20, 12, 30));
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertNull(rent);
    }

    @Test
    void addRentWithGivenDataRangeStartAfterExistingRentStartAndGivenEndEqualToExistingRentEndShouldFailed() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(5L);
        dto.setUserId(1L);
        dto.setStartDate(LocalDateTime.of(2022, 2, 13, 12, 30));
        dto.setEndDate(LocalDateTime.of(2022, 2, 15, 12, 30));
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertNull(rent);
    }

    @Test
    void addRentWithGivenDataRangeStartEqualExistingRentStartAndGivenEndBeforeToExistingRentEndShouldFailed() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(5L);
        dto.setUserId(1L);
        dto.setStartDate(LocalDateTime.of(2022, 2, 10, 12, 30));
        dto.setEndDate(LocalDateTime.of(2022, 2, 13, 12, 30));
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertNull(rent);
    }

    @Test
    void addRentWithGivenDataRangeStartAfterExistingRentStartAndGivenEndBeforeToExistingRentEndShouldFailed() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(5L);
        dto.setUserId(1L);
        dto.setStartDate(LocalDateTime.of(2022, 2, 11, 12, 30));
        dto.setEndDate(LocalDateTime.of(2022, 2, 13, 12, 30));
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertNull(rent);
    }

    @Test
    void addRentWithGivenDataRangeStartBeforeExistingRentStartAndGivenEndBetweenToExistingRentStartAndEndShouldFailed() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(5L);
        dto.setUserId(1L);
        dto.setStartDate(LocalDateTime.of(2022, 2, 8, 12, 30));
        dto.setEndDate(LocalDateTime.of(2022, 2, 13, 12, 30));
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertNull(rent);
    }

    @Test
    void addRentWithGivenDataRangeStartBetweenExistingRentStartAndEndAndGivenEndAfterToExistingRentEndShouldFailed() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(5L);
        dto.setUserId(1L);
        dto.setStartDate(LocalDateTime.of(2022, 2, 12, 12, 30));
        dto.setEndDate(LocalDateTime.of(2022, 2, 18, 12, 30));
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertNull(rent);
    }

    @Test
    void addRentWithGivenAvailableDataRangeStartAndEndShouldReturnInstanceOfRent() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(5L);
        dto.setUserId(1L);
        dto.setStartDate(LocalDateTime.of(2024, 2, 10, 12, 30));
        dto.setEndDate(LocalDateTime.of(2024, 2, 15, 12, 30));
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertAll(
                () -> assertInstanceOf(Rent.class, rent),
                () -> assertNotNull(rent)
        );
    }

    @Test
    void addRentWithGivenNotAvailableDataRangeStartAndEndShouldReturnNull() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(5L);
        dto.setUserId(1L);
        dto.setStartDate(LocalDateTime.of(2024, 2, 10, 12, 30));
        dto.setEndDate(LocalDateTime.of(2024, 2, 15, 12, 30));
        //when
        Rent rent = rentService.addRent(dto);
        Rent second = rentService.addRent(dto);
        //then
        assertAll(
                () -> assertInstanceOf(Rent.class, rent),
                () -> assertNull(second)
        );
    }

    @Test
    void addRentWithGivenDataRangeStartAndEndShouldReturnNullIfRentEndDateCollidesWitDelayBetweenRents() {
        //given
        RentAddDto dtoFirst = new RentAddDto();
        dtoFirst.setCarId(5L);
        dtoFirst.setUserId(1L);
        dtoFirst.setStartDate(LocalDateTime.of(2024, 2, 10, 12, 30));
        dtoFirst.setEndDate(LocalDateTime.of(2024, 2, 15, 12, 30));
        RentAddDto dtoSecond = new RentAddDto();
        dtoSecond.setCarId(5L);
        dtoSecond.setUserId(1L);
        dtoSecond.setStartDate(LocalDateTime.of(2024, 2, 2, 12, 30));
        dtoSecond.setEndDate(LocalDateTime.of(2024, 2, 10, 12, 20));
        //when
        Rent rent = rentService.addRent(dtoFirst);
        Rent second = rentService.addRent(dtoFirst);
        //then
        assertAll(
                () -> assertNotNull(rent),
                () -> assertNull(second)
        );
    }

    @Test
    void addRentWithGivenDataRangeStartAndEndShouldReturnRentIfRentEndDateNotCollidesWitDelayBetweenRents() {
        //given
        RentAddDto dtoFirst = new RentAddDto();
        dtoFirst.setCarId(5L);
        dtoFirst.setUserId(1L);
        dtoFirst.setStartDate(LocalDateTime.of(2024, 2, 10, 12, 30));
        dtoFirst.setEndDate(LocalDateTime.of(2024, 2, 15, 12, 30));
        RentAddDto dtoSecond = new RentAddDto();
        dtoSecond.setCarId(5L);
        dtoSecond.setUserId(1L);
        dtoSecond.setStartDate(LocalDateTime.of(2024, 2, 2, 12, 30));
        LocalDateTime end = LocalDateTime.of(2024, 2, 10, 12, 30);
        end = end.minusHours(Config.TIME_DELAY_UNTIL_NEXT_RENT);
        dtoSecond.setEndDate(end);
        //when
        Rent rent = rentService.addRent(dtoFirst);
        Rent second = rentService.addRent(dtoSecond);
        //then
        assertAll(
                () -> assertNotNull(rent, "first"),
                () -> assertNotNull(second, "second")
        );
    }

    @Test
    void addRentWithGivenDataRangeStartAndEndShouldReturnNullIfRentStartDateCollidesWitDelayBetweenRents() {
        //given
        RentAddDto dtoFirst = new RentAddDto();
        dtoFirst.setCarId(5L);
        dtoFirst.setUserId(1L);
        dtoFirst.setStartDate(LocalDateTime.of(2024, 2, 10, 12, 30));
        dtoFirst.setEndDate(LocalDateTime.of(2024, 2, 15, 12, 30));
        RentAddDto dtoSecond = new RentAddDto();
        dtoSecond.setCarId(5L);
        dtoSecond.setUserId(1L);
        dtoSecond.setStartDate(LocalDateTime.of(2024, 2, 15, 15, 30));
        dtoSecond.setEndDate(LocalDateTime.of(2024, 2, 20, 12, 20));
        //when
        Rent rent = rentService.addRent(dtoFirst);
        Rent second = rentService.addRent(dtoFirst);
        //then
        assertAll(
                () -> assertNotNull(rent),
                () -> assertNull(second)
        );
    }

    @Test
    void addRentWithGivenDataRangeStartAndEndShouldReturnRentIfRentStartDateNotCollidesWitDelayBetweenRents() {
        //given
        RentAddDto dtoFirst = new RentAddDto();
        dtoFirst.setCarId(5L);
        dtoFirst.setUserId(1L);
        dtoFirst.setStartDate(LocalDateTime.of(2024, 2, 10, 12, 30));
        dtoFirst.setEndDate(LocalDateTime.of(2024, 2, 15, 12, 30));
        RentAddDto dtoSecond = new RentAddDto();
        dtoSecond.setCarId(5L);
        dtoSecond.setUserId(1L);
        LocalDateTime start = LocalDateTime.of(2024, 2, 15, 12, 30);
        start = start.plusHours(Config.TIME_DELAY_UNTIL_NEXT_RENT);
        dtoSecond.setStartDate(start);
        LocalDateTime end = LocalDateTime.of(2024, 2, 20, 12, 30);
        dtoSecond.setEndDate(end);
        //when
        Rent rent = rentService.addRent(dtoFirst);
        Rent second = rentService.addRent(dtoSecond);
        //then
        assertAll(
                () -> assertNotNull(rent),
                () -> assertNotNull(second)
        );
    }

    @Test
    void addRentShouldAutomaticallyAddRentReturnReport() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(5L);
        dto.setUserId(1L);
        dto.setStartDate(LocalDateTime.of(2024, 2, 10, 12, 30));
        dto.setEndDate(LocalDateTime.of(2024, 2, 15, 12, 30));
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertNotNull(rent.getReport());
    }

    @Test
    void addRentShouldAutomaticallyCalculatePrice() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(5L);
        dto.setUserId(8L);
        dto.setStartDate(LocalDateTime.of(2024, 2, 10, 12, 30));
        dto.setEndDate(LocalDateTime.of(2024, 2, 15, 12, 30));
        double carPricePerDay = 150.00;
        int rentDays = 5;
        BigDecimal finalPrice = BigDecimal.valueOf(rentDays * carPricePerDay);
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertAll(
                () -> assertNotNull(rent.getFinalPrice()),
                () -> assertEquals(finalPrice, rent.getFinalPrice())
        );
    }

    @Test
    void addRentShouldAutomaticallyCalculatePriceWithDiscountForRentsAtLeastSevenDays() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(5L);
        dto.setUserId(1L);
        dto.setStartDate(LocalDateTime.of(2024, 2, 10, 12, 30));
        dto.setEndDate(LocalDateTime.of(2024, 2, 18, 12, 30));
        double carPricePerDay = 150.00;
        int rentDays = 8;
        double discount = 0.03;
        BigDecimal finalPrice =
                BigDecimal.valueOf((rentDays * carPricePerDay) - (rentDays * carPricePerDay) * discount);
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertAll(
                () -> assertNotNull(rent.getFinalPrice()),
                () -> assertEquals(finalPrice, rent.getFinalPrice())
        );
    }

    @Test
    void addRentShouldAutomaticallyCalculatePriceWithDiscountForUsersWithTwentySixYearsOld() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(5L);
        dto.setUserId(2L);
        dto.setStartDate(LocalDateTime.of(2024, 2, 10, 12, 30));
        dto.setEndDate(LocalDateTime.of(2024, 2, 15, 12, 30));
        double carPricePerDay = 150.00;
        int rentDays = 5;
        double discount = 0.05;
        BigDecimal finalPrice =
                BigDecimal.valueOf((rentDays * carPricePerDay) - (rentDays * carPricePerDay) * discount);
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertAll(
                () -> assertNotNull(rent.getFinalPrice()),
                () -> assertEquals(finalPrice, rent.getFinalPrice())
        );
    }

    @Test
    void addRentShouldFailedAndReturnNullIfGivenCarIdNotExist() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(555L);
        dto.setUserId(2L);
        dto.setStartDate(LocalDateTime.of(2024, 2, 10, 12, 30));
        dto.setEndDate(LocalDateTime.of(2024, 2, 15, 12, 30));
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertNull(rent);
    }

    @Test
    void addRentShouldFailedAndReturnNullIfGivenUserIdNotExist() {
        //given
        RentAddDto dto = new RentAddDto();
        dto.setCarId(2L);
        dto.setUserId(666L);
        dto.setStartDate(LocalDateTime.of(2024, 2, 10, 12, 30));
        dto.setEndDate(LocalDateTime.of(2024, 2, 15, 12, 30));
        //when
        Rent rent = rentService.addRent(dto);
        //then
        assertNull(rent);
    }

    @Test
    void getAllShouldReturnPageWithGivenPageAndSize() {
        //given
        int pageNumber = 0;
        int size = 6;
        PageRequest page = PageRequest.of(pageNumber, size);
        //when
        Page<RentDto> rents = rentService.getAll(page);
        //then
        assertAll(
                () -> assertEquals(pageNumber, rents.getPageable().getPageNumber()),
                () -> assertEquals(size, rents.getPageable().getPageSize())
        );
    }

    @Test
    void getAllShouldReturnPageWithAllRentsFromDataBase() {
        //given
        int pageNumber = 0;
        int size = 6;
        PageRequest page = PageRequest.of(pageNumber, size);
        int expectingRentsQuantity = 13;
        //when
        Page<RentDto> rents = rentService.getAll(page);
        //then
        assertEquals(expectingRentsQuantity, rents.getTotalElements());
    }

    @Test
    void getAllShouldReturnEmptyPageWhenDatabaseIsEmpty() {
        //given
        int pageNumber = 0;
        int size = 6;
        PageRequest page = PageRequest.of(pageNumber, size);
        int expectingRentsQuantity = 0;
        //when
        cleanDb();
        Page<RentDto> rents = rentService.getAll(page);
        //then
        assertEquals(expectingRentsQuantity, rents.getTotalElements());
        prepareDb();
    }

    @Test
    void getAllShouldReturnPageWithRentDto() {
        //given
        int pageNumber = 0;
        int size = 6;
        PageRequest page = PageRequest.of(pageNumber, size);
        //when
        Page<RentDto> rents = rentService.getAll(page);
        //then
        assertAll(
                () -> assertNotNull(rents.getContent().get(0)),
                () -> assertInstanceOf(RentDto.class, rents.getContent().get(0))
        );
    }

    @Test
    void findByIdWithNotExistingIdShouldReturnNull() {
        //given
        Long id = Long.MAX_VALUE;
        //when
        RentDto rent = rentService.findById(id);
        //then
        assertNull(rent);
    }

    @Test
    void findByIdWithExistingIdShouldReturnRentDto() {
        //given
        Long id = 1L;
        //when
        RentDto rent = rentService.findById(id);
        //then
        assertAll(
                () -> assertNotNull(rent),
                () -> assertInstanceOf(RentDto.class, rent)
        );
    }

    @Test
    void findByIdWithExistingIdShouldReturnRentDtoWithTheSameId() {
        //given
        Long id = 1L;
        //when
        RentDto rent = rentService.findById(id);
        //then
        assertEquals(id, rent.getId());
    }

    @Test
    void searchShouldMissDeletedRents() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentSearchDto dto = new RentSearchDto();
        int expectingRentsQuantity = 12;
        //when
        Page<RentDto> rents = rentService.search(page, dto);
        //then
        assertEquals(expectingRentsQuantity, rents.getTotalElements());
    }

    @Test
    void searchWithGivenRentIdShouldMissItWhenRentIsDeleted() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentSearchDto dto = new RentSearchDto();
        dto.setId(13L);
        int expectingRentsQuantity = 0;
        //when
        Page<RentDto> rents = rentService.search(page, dto);
        //then
        assertEquals(expectingRentsQuantity, rents.getTotalElements());
    }

    @Test
    void searchWithoutAnyParameterShouldReturnAllRentsInDatabase() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentSearchDto dto = new RentSearchDto();
        int expectingRentsQuantity = 12;
        //when
        Page<RentDto> rents = rentService.search(page, dto);
        //then
        assertEquals(expectingRentsQuantity, rents.getTotalElements());
    }

    @Test
    void searchWithoutAnyParameterAndEmptyDatabaseShouldReturnEmptyPage() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentSearchDto dto = new RentSearchDto();
        int expectingRentsQuantity = 0;
        //when
        cleanDb();
        Page<RentDto> rents = rentService.search(page, dto);
        //then
        assertEquals(expectingRentsQuantity, rents.getTotalElements());
        prepareDb();
    }

    @Test
    void searchShouldReturnPageWithRentDtoWhenFindAny() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentSearchDto dto = new RentSearchDto();
        //when
        Page<RentDto> rents = rentService.search(page, dto);
        //then
        assertAll(
                () -> assertThat(rents.getContent().size() > 0),
                () -> assertInstanceOf(RentDto.class, rents.getContent().get(0))
        );
    }

    @Test
    void searchShouldReturnOneRentDtoWhenRentWithGivenIdExist() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentSearchDto dto = new RentSearchDto();
        Long id = 1L;
        dto.setId(id);
        int expectingRentQuantity = 1;
        //when
        Page<RentDto> rents = rentService.search(page, dto);
        //then
        assertAll(
                () -> assertEquals(expectingRentQuantity, rents.getTotalElements()),
                () -> assertEquals(id, rents.getContent().get(0).getId())
        );
    }

    @Test
    void searchShouldReturnMatchingRentsCarToGivenCarId() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentSearchDto dto = new RentSearchDto();
        Long carId = 1L;
        dto.setCarId(carId);
        int expectingRentQuantity = 3;
        //when
        Page<RentDto> rents = rentService.search(page, dto);
        //then
        assertAll(
                () -> assertEquals(expectingRentQuantity, rents.getTotalElements()),
                () -> assertEquals(carId, rents.getContent().get(0).getCar().getId())
        );
    }

    @Test
    void searchShouldReturnMatchingRentsCarToGivenUserId() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentSearchDto dto = new RentSearchDto();
        Long userId = 1L;
        dto.setUserId(userId);
        int expectingRentQuantity = 4;
        //when
        Page<RentDto> rents = rentService.search(page, dto);
        //then
        assertAll(
                () -> assertEquals(expectingRentQuantity, rents.getTotalElements()),
                () -> assertEquals(userId, rents.getContent().get(0).getCar().getId())
        );
    }

    @Test
    void searchWithGivenConfirmedTrueShouldReturnConfirmedRents() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentSearchDto dto = new RentSearchDto();
        boolean confirmed = true;
        dto.setConfirmed(confirmed);
        int expectingRentQuantity = 8;
        //when
        Page<RentDto> rents = rentService.search(page, dto);
        //then
        assertAll(
                () -> assertEquals(expectingRentQuantity, rents.getTotalElements()),
                () -> assertEquals(confirmed, rents.getContent().get(0).isConfirmed())
        );
    }

    @Test
    void searchWithGivenConfirmedFalseShouldReturnNotConfirmedRents() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentSearchDto dto = new RentSearchDto();
        boolean confirmed = false;
        dto.setConfirmed(confirmed);
        int expectingRentQuantity = 4;
        //when
        Page<RentDto> rents = rentService.search(page, dto);
        //then
        assertAll(
                () -> assertEquals(expectingRentQuantity, rents.getTotalElements()),
                () -> assertEquals(confirmed, rents.getContent().get(0).isConfirmed())
        );
    }

    @Test
    void searchWithGivenReturnedTrueShouldReturnReturnedRents() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentSearchDto dto = new RentSearchDto();
        boolean returned = true;
        dto.setReturned(returned);
        int expectingRentQuantity = 8;
        //when
        Page<RentDto> rents = rentService.search(page, dto);
        //then
        assertAll(
                () -> assertEquals(expectingRentQuantity, rents.getTotalElements()),
                () -> assertEquals(returned, rents.getContent().get(0).isReturned())
        );
    }

    @Test
    void searchWithGivenReturnedFalseShouldReturnNotReturnedRents() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentSearchDto dto = new RentSearchDto();
        boolean returned = false;
        dto.setReturned(returned);
        int expectingRentQuantity = 4;
        //when
        Page<RentDto> rents = rentService.search(page, dto);
        //then
        assertAll(
                () -> assertEquals(expectingRentQuantity, rents.getTotalElements()),
                () -> assertEquals(returned, rents.getContent().get(0).isReturned())
        );
    }

    @Test
    void searchWithGivenDamagedFalseShouldReturnNotDamaged() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentSearchDto dto = new RentSearchDto();
        boolean damaged = false;
        dto.setDamaged(damaged);
        int expectingRentQuantity = 10;
        //when
        Page<RentDto> rents = rentService.search(page, dto);
        //then
        assertEquals(expectingRentQuantity, rents.getTotalElements());
    }

    @Test
    void searchWithGivenDamagedTrueShouldReturnDamaged() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentSearchDto dto = new RentSearchDto();
        boolean damaged = true;
        dto.setDamaged(damaged);
        int expectingRentQuantity = 2;
        //when
        Page<RentDto> rents = rentService.search(page, dto);
        //then
        assertEquals(expectingRentQuantity, rents.getTotalElements());
    }

    @Test
    void updateRentShouldReturnNullWhenRentWithGivenIdNotExists() {
        //given
        RentUpdateDto dto = new RentUpdateDto();
        dto.setId(Long.MAX_VALUE);
        //when
        RentDto rent = rentService.updateRent(dto);
        //then
        assertNull(rent);
    }

    @Test
    void updateRentShouldReturnRentDtoWhenRentWithGivenIdExists() {
        //given
        RentUpdateDto dto = new RentUpdateDto();
        Long id = 1L;
        dto.setId(id);
        //when
        RentDto rent = rentService.updateRent(dto);
        //then
        assertNotNull(rent);
    }

    @Test
    void updateRentShouldReturnRentDtoWithUpdatedCommentAndFinalPriceFields() {
        //given
        RentUpdateDto dto = new RentUpdateDto();
        Long id = 1L;
        RentAdminViewDto beforeUpdate = rentService.findByIdAdminView(id);
        String commentBefore = beforeUpdate.getComment();
        BigDecimal priceBefore = beforeUpdate.getFinalPrice();
        dto.setId(id);
        dto.setComment("NEW COMMENT");
        dto.setFinalPrice(BigDecimal.valueOf(666.66));
        RentAdminViewDto afterUpdate;
        //when
        rentService.updateRent(dto);
        afterUpdate = rentService.findByIdAdminView(id);
        //then
        assertAll(
                () -> assertNotEquals(commentBefore, afterUpdate.getComment()),
                () -> assertNotEquals(priceBefore, afterUpdate.getFinalPrice()),
                () -> assertEquals("NEW COMMENT", afterUpdate.getComment()),
                () -> assertEquals(BigDecimal.valueOf(666.66), afterUpdate.getFinalPrice())
        );
    }

    @Test
    void updateRentShouldReturnRentDtoWithUpdatedConfirmedAndReturnFields() {
        //given
        RentUpdateDto dto = new RentUpdateDto();
        Long id = 1L;
        RentAdminViewDto beforeUpdate = rentService.findByIdAdminView(id);
        boolean confirmedBefore = beforeUpdate.isConfirmed();
        boolean returnedBefore = beforeUpdate.isReturned();
        dto.setId(id);
        dto.setConfirmed(true);
        dto.setReturned(false);
        RentAdminViewDto afterUpdate;
        //when
        rentService.updateRent(dto);
        afterUpdate = rentService.findByIdAdminView(id);
        //then
        assertAll(
                () -> assertNotEquals(confirmedBefore, afterUpdate.getComment()),
                () -> assertNotEquals(returnedBefore, afterUpdate.getFinalPrice()),
                () -> assertTrue(afterUpdate.isConfirmed()),
                () -> assertFalse(afterUpdate.isReturned())
        );
    }

    @Test
    void deleteRentShouldReturnTrueWhenSuccessfulDeletedExistingRent() {
        //given
        Long id = 1L;
        //when
        boolean check = rentService.deleteRent(id);
        //then
        assertTrue(check);
    }

    @Test
    void deleteRentShouldReturnFalseWhenRentWithGivenIdNotExist() {
        //given
        Long id = Long.MAX_VALUE;
        //when
        boolean check = rentService.deleteRent(id);
        //then
        assertFalse(check);
    }

    @Test
    void deleteRentShouldChangeDeleteFieldToTrue() {
        //given
        Long id = 1L;
        RentAdminViewDto dto = rentService.findByIdAdminView(id);
        boolean beforeDelete = dto.isDeleted();
        //when
        rentService.deleteRent(id);
        boolean afterDelete = rentService.findByIdAdminView(id).isDeleted();
        //then
        assertAll(
                () -> assertFalse(beforeDelete),
                () -> assertTrue(afterDelete)
        );

    }
}
