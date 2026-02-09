package com.kilicciogluemre.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kilicciogluemre.Dto.Request.OrderRequestDto;
import com.kilicciogluemre.Dto.Response.OrderResponseDto;
import com.kilicciogluemre.Dto.Response.TopStoreRevenueResponseDto;
import com.kilicciogluemre.service.IOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/orders")
@Validated
public class OrderController {
	
	@Autowired
    private IOrderService orderService;


	@PostMapping("/create")
	 public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto requestDto) {

	        OrderResponseDto response = orderService.createOrder(requestDto);
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	 }
	
	@GetMapping("/totals")
	public BigDecimal getTotalRevenue() {
		return orderService.getTotalRevenue();
	}
	
	@GetMapping("/totals/{storeId}")
	public BigDecimal getStoreRevenue(@PathVariable Long storeId) {
		return orderService.getTotalRevenueByStore(storeId);
	}
	
	@GetMapping("/top-revenue")
	public TopStoreRevenueResponseDto getTopRevenueStore() {
		return orderService.getTopRevenueStore();
	}
}
