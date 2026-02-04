package com.kilicciogluemre.Dto.Response;

public class OrderItemResponseDto {

	private Long id;
	
	private Integer quantity;
	
	private ProductResponseDto product;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public ProductResponseDto getProduct() {
		return product;
	}
	
	public void setProduct(ProductResponseDto product) {
		this.product = product;
	}

}
