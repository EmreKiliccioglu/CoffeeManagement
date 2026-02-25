package com.kilicciogluemre.Mapper;

import com.kilicciogluemre.Dto.Request.ProductRequestDto;
import com.kilicciogluemre.Dto.Response.ProductResponseDto;
import com.kilicciogluemre.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDto toDto(ProductEntity entity){
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setActive(entity.getActive());
        return dto;
    }

    public ProductEntity toEntity(ProductRequestDto dto){
        ProductEntity entity = new ProductEntity();
        entity.setName(dto.getName());
        entity.setActive(true);
        entity.setDeleted(false);
        return entity;
    }

    public void updateEntityFromDto(ProductRequestDto dto, ProductEntity entity){
        if(dto.getName() != null){
            entity.setName(dto.getName());
        }
    }
}
