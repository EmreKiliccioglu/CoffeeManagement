package com.kilicciogluemre.Dto.Response;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponseDto {

	private Long id;
	private BigDecimal totalPrice;

	private UserResponseDto user;
	private StoreResponseDto store;
	private List<OrderItemResponseDto> items;

	public OrderResponseDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public UserResponseDto getUser() {
		return user;
	}

	public void setUser(UserResponseDto user) {
		this.user = user;
	}

	public StoreResponseDto getStore() {
		return store;
	}

	public void setStore(StoreResponseDto store) {
		this.store = store;
	}

	public List<OrderItemResponseDto> getItems() {
		return items;
	}

	public void setItems(List<OrderItemResponseDto> items) {
		this.items = items;
	}
}
