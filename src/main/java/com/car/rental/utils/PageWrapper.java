package com.car.rental.utils;

import com.car.rental.car.dto.CarDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class PageWrapper {

    public static Page listToPage(Pageable pageable, List list){
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        final Page<CarDto> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
        return page;
    }

}
