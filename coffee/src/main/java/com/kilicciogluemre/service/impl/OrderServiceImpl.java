package com.kilicciogluemre.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kilicciogluemre.Mapper.OrderMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kilicciogluemre.Dto.Request.OrderItemRequestDto;
import com.kilicciogluemre.Dto.Request.OrderRequestDto;
import com.kilicciogluemre.Dto.Response.OrderItemResponseDto;
import com.kilicciogluemre.Dto.Response.OrderResponseDto;
import com.kilicciogluemre.Dto.Response.ProductResponseDto;
import com.kilicciogluemre.Dto.Response.StoreResponseDto;
import com.kilicciogluemre.Dto.Response.TopStoreRevenueResponseDto;
import com.kilicciogluemre.Dto.Response.UserResponseDto;
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

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private StoreProductRepository storeProductRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderMapper orderMapper;

	@Override
	@Transactional
	public OrderResponseDto createOrder(OrderRequestDto requestDto) {

		UserEntity user = userRepository.findById(requestDto.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		StoreEntity store = storeRepository.findById(requestDto.getStoreId())
				.orElseThrow(() -> new RuntimeException("Store not found"));

		OrderEntity order = new OrderEntity();
		order.setUser(user);
		order.setStore(store);
		order.setTotalPrice(BigDecimal.ZERO);

		OrderEntity savedOrder = orderRepository.save(order);

		BigDecimal totalPrice = BigDecimal.ZERO;
		List<OrderItemResponseDto> itemResponses = new ArrayList<>();

		for (OrderItemRequestDto itemDto : requestDto.getItems()) {

			StoreProductEntity storeProduct = storeProductRepository
					.findByStore_IdAndProduct_Id(store.getId(), itemDto.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found in this store"));

			if (!storeProduct.getActive()) {
				throw new RuntimeException("Product is not active in this store");
			}

			if (storeProduct.getStock() < itemDto.getQuantity()) {
				throw new RuntimeException("Insufficient stock");
			}

			OrderItemEntity orderItem = new OrderItemEntity();
			orderItem.setOrder(savedOrder);
			orderItem.setStoreProduct(storeProduct);
			orderItem.setQuantity(itemDto.getQuantity());
			orderItem.setPriceAtOrderTime(storeProduct.getPrice());

			OrderItemEntity savedItem = orderItemRepository.save(orderItem);

			BigDecimal itemTotal = storeProduct.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
			totalPrice = totalPrice.add(itemTotal);

			storeProduct.setStock(storeProduct.getStock() - itemDto.getQuantity());
			storeProductRepository.save(storeProduct);

			OrderItemResponseDto itemResponse = new OrderItemResponseDto();
			itemResponse.setId(savedItem.getId());
			itemResponse.setQuantity(savedItem.getQuantity());
			itemResponse.setPriceAtOrderTime(savedItem.getPriceAtOrderTime());

			ProductResponseDto productDto = new ProductResponseDto();
			BeanUtils.copyProperties(storeProduct.getProduct(), productDto);
			itemResponse.setProduct(productDto);

			itemResponses.add(itemResponse);
		}

		savedOrder.setTotalPrice(totalPrice);
		orderRepository.save(savedOrder);

		OrderResponseDto response = new OrderResponseDto();
		response.setId(savedOrder.getId());
		response.setTotalPrice(totalPrice);
		response.setItems(itemResponses);

		UserResponseDto userDto = new UserResponseDto();
		BeanUtils.copyProperties(user, userDto);
		response.setUser(userDto);

		StoreResponseDto storeDto = new StoreResponseDto();
		BeanUtils.copyProperties(store, storeDto);
		response.setStore(storeDto);

		return response;
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
