package com.kilicciogluemre.Mapper;

import com.kilicciogluemre.Dto.Request.StoreRequestDto;
import com.kilicciogluemre.Dto.Response.StoreResponseDto;
import com.kilicciogluemre.entity.StoreEntity;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {

    public StoreEntity toEntity(StoreRequestDto dto){
        if(dto==null) return null;

        StoreEntity entity = new StoreEntity();
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setActive(true);
        entity.setDeleted(false);

        return entity;
    }

    public StoreResponseDto toResponse(StoreEntity entity){
        if(entity==null) return null;

        StoreResponseDto dto = new StoreResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());

        return dto;
    }
}
