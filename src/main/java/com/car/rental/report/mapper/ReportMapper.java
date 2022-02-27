package com.car.rental.rent.mapper;

import com.car.rental.car.mapper.CarMapper;
import com.car.rental.report.ReturnReport;
import com.car.rental.report.dto.ReturnReportDto;
import com.car.rental.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, CarMapper.class})
public interface ReportMapper {
    ReportMapper REPORT_DTO_MAPPER = Mappers.getMapper(ReportMapper.class);

    ReturnReportDto returnReportToReturnReportDto(ReturnReport report);

}
