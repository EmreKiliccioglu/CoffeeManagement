package com.kilicciogluemre.service.impl;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.kilicciogluemre.Dto.Request.OrderItemRequestDto;
import com.kilicciogluemre.Dto.Request.OrderRequestDto;
import com.kilicciogluemre.Dto.Response.OrderResponseDto;
import com.kilicciogluemre.entity.OrderEntity;
import com.kilicciogluemre.entity.OrderItemEntity;
import com.kilicciogluemre.entity.ProductEntity;
import com.kilicciogluemre.entity.StoreEntity;
import com.kilicciogluemre.entity.UserEntity;
import com.kilicciogluemre.repository.OrderRepository;
import com.kilicciogluemre.repository.ProductRepository;
import com.kilicciogluemre.repository.StoreRepository;
import com.kilicciogluemre.repository.UserRepository;
import com.kilicciogluemre.service.IOrderService;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements IOrderService {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final StoreRepository storeRepository;

	public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
			ProductRepository productRepository, StoreRepository storeRepository) {

		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.storeRepository = storeRepository;
	}

	@Override
	@Transactional
	public OrderResponseDto createOrder(OrderRequestDto request) {

		UserEntity user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new RuntimeException("User Not Found"));

		StoreEntity store = storeRepository.findById(request.getStoreId())
				.orElseThrow(() -> new RuntimeException("Store Not Found"));

		OrderEntity order = new OrderEntity();
		order.setUser(user);
		order.setStore(store);

		BigDecimal totalPrice = BigDecimal.ZERO;
		List<OrderItemEntity> orderItems = new ArrayList<>();

		for (OrderItemRequestDto itemDto : request.getItems()) {

			ProductEntity product = productRepository.findById(itemDto.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found"));

			BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));

			totalPrice = totalPrice.add(itemTotal);

			OrderItemEntity orderItem = new OrderItemEntity();
			orderItem.setOrder(order);
			orderItem.setProduct(product);
			orderItem.setQuantity(itemDto.getQuantity());

			orderItems.add(orderItem);
		}

		order.setTotalPrice(totalPrice);

		OrderEntity savedOrder = orderRepository.save(order);

		return mapToOrderResponse(savedOrder);
	}

	private OrderResponseDto mapToOrderResponse(OrderEntity order) {
		OrderResponseDto dto = new OrderResponseDto();
		dto.setId(order.getId());
		dto.setTotalPrice(order.getTotalPrice());
		return dto;
	}
}
