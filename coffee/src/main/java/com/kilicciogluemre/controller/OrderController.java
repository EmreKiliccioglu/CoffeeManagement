package com.kilicciogluemre.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kilicciogluemre.Dto.Request.OrderRequestDto;
import com.kilicciogluemre.Dto.Response.OrderResponseDto;
import com.kilicciogluemre.service.IOrderService;

@RestController
@RequestMapping("/rest/api/orders")
public class OrderController {

	private final IOrderService orderService;

	public OrderController(IOrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto request) {
		OrderResponseDto response = orderService.createOrder(request);
		return ResponseEntity.ok(response);
	}
}
