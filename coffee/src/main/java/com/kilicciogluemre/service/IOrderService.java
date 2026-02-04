package com.kilicciogluemre.service;

import com.kilicciogluemre.Dto.Request.OrderRequestDto;
import com.kilicciogluemre.Dto.Response.OrderResponseDto;

public interface IOrderService {

	OrderResponseDto createOrder(OrderRequestDto request);
}
