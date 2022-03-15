package com.car.rental.report.mapper;

import com.car.rental.car.mapper.CarMapper;
import com.car.rental.report.ReturnReport;
import com.car.rental.report.dto.ReturnReportAddDto;
import com.car.rental.report.dto.ReturnReportDto;
import com.car.rental.report.dto.ReturnReportUpdateDto;
import com.car.rental.rent.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, CarMapper.class})
public interface ReturnReportMapper {
    ReturnReportMapper REPORT_DTO_MAPPER = Mappers.getMapper(ReturnReportMapper.class);

    ReturnReportDto returnReportToReturnReportDto(ReturnReport report);

    ReturnReport returnReportAddDtoToReturnReport(ReturnReportAddDto reportAddDto);

    ReturnReport returnReportUpdateToReturnReport(ReturnReportUpdateDto returnReportUpdateDto);

}
