package com.oxesocial.ms_users.api.dto.mapper;

import com.oxesocial.ms_users.api.dto.PageableDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class PageableMapper {

    public PageableMapper() {
    }

    public static PageableDto toDto(Page page) {
        return new ModelMapper().map(page, PageableDto.class);
    }
}
