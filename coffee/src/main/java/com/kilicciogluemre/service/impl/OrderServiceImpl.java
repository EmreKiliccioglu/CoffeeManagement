package com.kilicciogluemre.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kilicciogluemre.GlobalException.Exceptions;
import com.kilicciogluemre.Mapper.OrderMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.kilicciogluemre.Dto.Request.OrderItemRequestDto;
import com.kilicciogluemre.Dto.Request.OrderRequestDto;
import com.kilicciogluemre.Dto.Response.OrderResponseDto;
import com.kilicciogluemre.Dto.Response.TopStoreRevenueResponseDto;
import com.kilicciogluemre.entity.OrderEntity;
import com.kilicciogluemre.entity.OrderItemEntity;
import com.kilicciogluemre.entity.StoreEntity;
import com.kilicciogluemre.entity.StoreProductEntity;
import com.kilicciogluemre.entity.UserEntity;
import com.kilicciogluemre.repository.OrderItemRepository;
import com.kilicciogluemre.repository.OrderRepository;
import com.kilicciogluemre.repository.StoreProductRepository;
import com.kilicciogluemre.repository.StoreRepository;
import com.kilicciogluemre.repository.TopStoreRevenueProjection;
import com.kilicciogluemre.repository.UserRepository;
import com.kilicciogluemre.service.IOrderService;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements IOrderService {

	private final UserRepository userRepository;
	private final StoreRepository storeRepository;
	private final StoreProductRepository storeProductRepository;
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final OrderMapper orderMapper;

	public OrderServiceImpl(UserRepository userRepository,
							StoreRepository storeRepository,
							StoreProductRepository storeProductRepository,
							OrderRepository orderRepository,
							OrderItemRepository orderItemRepository,
							OrderMapper orderMapper) {

		this.userRepository = userRepository;
		this.storeRepository = storeRepository;
		this.storeProductRepository = storeProductRepository;
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.orderMapper = orderMapper;
	}

	@Override
	@Transactional
	public OrderResponseDto createOrder(OrderRequestDto requestDto) {

		UserEntity user = userRepository.findById(requestDto.getUserId())
				.orElseThrow(() -> new Exceptions.ResourceNotFoundException("User not found"));

		StoreEntity store = storeRepository.findById(requestDto.getStoreId())
				.orElseThrow(() -> new Exceptions.ResourceNotFoundException("Store not found"));

		OrderEntity order = new OrderEntity();
		order.setUser(user);
		order.setStore(store);

		BigDecimal totalPrice = BigDecimal.ZERO;
		List<OrderItemEntity> orderItems = new ArrayList<>();

		for (OrderItemRequestDto itemDto : requestDto.getItems()) {

			StoreProductEntity storeProduct = storeProductRepository
					.findByStore_IdAndProduct_Id(store.getId(), itemDto.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found"));

			if (!storeProduct.getActive())
				throw new Exceptions.InactiveProductException("Product inactive");

			if (storeProduct.getStock() < itemDto.getQuantity())
				throw new RuntimeException("Insufficient stock");

			OrderItemEntity item = new OrderItemEntity();
			item.setOrder(order);
			item.setStoreProduct(storeProduct);
			item.setQuantity(itemDto.getQuantity());
			item.setPriceAtOrderTime(storeProduct.getPrice());

			orderItems.add(item);

			totalPrice = totalPrice.add(
					storeProduct.getPrice()
							.multiply(BigDecimal.valueOf(itemDto.getQuantity()))
			);

			storeProduct.setStock(
					storeProduct.getStock() - itemDto.getQuantity()
			);
		}

		order.setItems(orderItems);
		order.setTotalPrice(totalPrice);

		order = orderRepository.save(order);

		return orderMapper.toDto(order);
	}

	@Override
	public BigDecimal getTotalRevenue() {

		return orderRepository.getTotalRevenue();
	}

	@Override
	public BigDecimal getTotalRevenueByStore(Long storeId) {

		storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Store Not Found"));
		return orderRepository.getTotalRevenueByStoreId(storeId);
	}

	@Override
	public TopStoreRevenueResponseDto getTopRevenueStore() {

		List<TopStoreRevenueProjection> result = orderRepository.findStoreRevenuesDesc();

		if (result.isEmpty()) {
			throw new RuntimeException("No orders found");
		}

		TopStoreRevenueProjection top = result.get(0);

		StoreEntity store = storeRepository.findById(top.getStoreId())
				.orElseThrow(() -> new RuntimeException("Store not found"));

		TopStoreRevenueResponseDto response = new TopStoreRevenueResponseDto();
		response.setStoreId(store.getId());
		response.setStoreName(store.getName());
		response.setStoreAddress(store.getAddress());
		response.setTotalRevenue(top.getTotalRevenue());

		return response;
	}

	@Override
	public List<OrderResponseDto> getOrdersByUserId(Long userId) {

		List<OrderEntity> orders = orderRepository.findByUser_Id(userId);

		return orders.stream()
				.map(orderMapper::toDto)
				.toList();
	}
}
