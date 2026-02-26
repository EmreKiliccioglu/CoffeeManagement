package com.kilicciogluemre.Mapper;

import org.springframework.stereotype.Component;
import com.kilicciogluemre.entity.StoreProductEntity;
import com.kilicciogluemre.Dto.Response.StoreProductResponseDto;

@Component
public class StoreProductMapper {

    public StoreProductResponseDto toDto(StoreProductEntity sp) {
        StoreProductResponseDto dto = new StoreProductResponseDto();
        dto.setId(sp.getId());

        if (sp.getStore() != null) {
            dto.setStoreId(sp.getStore().getId());
            dto.setStoreName(sp.getStore().getName());
        }

        if (sp.getProduct() != null) {
            dto.setProductId(sp.getProduct().getId());
            dto.setProductName(sp.getProduct().getName());
        }

        dto.setPrice(sp.getPrice());
        dto.setStock(sp.getStock());
        dto.setActive(sp.getActive());

        return dto;
    }
}
