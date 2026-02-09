package com.kilicciogluemre.service;

import java.util.List;

import com.kilicciogluemre.Dto.Request.ProductRequestDto;
import com.kilicciogluemre.Dto.Response.ProductResponseDto;

public interface IProductService {
	
	ProductResponseDto createProduct(ProductRequestDto productRequestDto);
	
	List<ProductResponseDto> getAllProducts();
	
	void deleteProductById(Long id);
	
	ProductResponseDto getProductById(Long id);
	
	List<ProductResponseDto> getProductsByName(String name);
}
