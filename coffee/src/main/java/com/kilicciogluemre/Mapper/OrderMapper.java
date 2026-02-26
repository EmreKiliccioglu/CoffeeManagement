package com.kilicciogluemre.Mapper;

import com.kilicciogluemre.Dto.Response.OrderItemResponseDto;
import com.kilicciogluemre.Dto.Response.OrderResponseDto;
import com.kilicciogluemre.entity.OrderEntity;
import com.kilicciogluemre.entity.OrderItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    private final UserMapper userMapper;
    private final StoreMapper storeMapper;
    private final ProductMapper productMapper;

    public OrderMapper(UserMapper userMapper,
                       StoreMapper storeMapper,
                       ProductMapper productMapper) {
        this.userMapper = userMapper;
        this.storeMapper = storeMapper;
        this.productMapper = productMapper;
    }

    public OrderResponseDto toDto(OrderEntity order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setUser(userMapper.toDto(order.getUser()));
        dto.setStore(storeMapper.toResponse(order.getStore()));

        dto.setItems(
                order.getItems() == null
                        ? List.of()
                        : order.getItems().stream()
                        .map(this::orderItemToDto)
                        .toList()
        );

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
