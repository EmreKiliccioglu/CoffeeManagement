package com.kilicciogluemre.Mapper;

import com.kilicciogluemre.Dto.Response.OrderItemResponseDto;
import com.kilicciogluemre.Dto.Response.OrderResponseDto;
import com.kilicciogluemre.entity.OrderEntity;
import com.kilicciogluemre.entity.OrderItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private ProductMapper productMapper;

    public OrderResponseDto toDto(OrderEntity order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setUser(userMapper.toDto(order.getUser()));
        dto.setStore(storeMapper.toResponse(order.getStore()));

        dto.setItems(order.getItems().stream()
                .map(this::orderItemToDto)
                .collect(Collectors.toList()));

        return dto;
    }

    public OrderItemResponseDto orderItemToDto(OrderItemEntity item) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setPriceAtOrderTime(item.getPriceAtOrderTime());
        dto.setProduct(productMapper.toDto(item.getStoreProduct().getProduct()));
        return dto;
    }
}
