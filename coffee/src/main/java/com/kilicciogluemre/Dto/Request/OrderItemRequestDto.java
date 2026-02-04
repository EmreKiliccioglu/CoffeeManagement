package com.kilicciogluemre.Dto.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderItemRequestDto {

	@NotNull
	private Long productId;

	@Min(1)
	private Integer quantity;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long prodcutId) {
		this.productId = prodcutId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
