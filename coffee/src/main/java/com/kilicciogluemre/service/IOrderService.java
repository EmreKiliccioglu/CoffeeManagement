package com.kilicciogluemre.service;

import java.math.BigDecimal;

import com.kilicciogluemre.Dto.Request.OrderRequestDto;
import com.kilicciogluemre.Dto.Response.OrderResponseDto;
import com.kilicciogluemre.Dto.Response.TopStoreRevenueResponseDto;

public interface IOrderService {

	OrderResponseDto createOrder(OrderRequestDto request);
	
	BigDecimal getTotalRevenue();
	
	BigDecimal getTotalRevenueByStore(Long storeId);
	
	TopStoreRevenueResponseDto getTopRevenueStore();
}
