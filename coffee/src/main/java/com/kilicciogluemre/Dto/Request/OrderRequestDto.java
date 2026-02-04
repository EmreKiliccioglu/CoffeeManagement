package com.kilicciogluemre.Dto.Request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class OrderRequestDto {

	@NotNull
	private Long userId;

	@NotNull
	private Long storeId;

	@NotNull
	private List<OrderItemRequestDto> items;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public List<OrderItemRequestDto> getItems() {
		return items;
	}

	public void setItems(List<OrderItemRequestDto> items) {
		this.items = items;
	}
}
