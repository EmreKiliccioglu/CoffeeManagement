package com.kilicciogluemre.service;

import java.util.List;

import com.kilicciogluemre.Dto.Request.StoreProductRequestDto;
import com.kilicciogluemre.Dto.Response.StoreProductResponseDto;

public interface IStoreProductService {
	
    StoreProductResponseDto addProductToStore(StoreProductRequestDto dto);

	List<StoreProductResponseDto> getProductsByStore(Long storeId);
	
	List<StoreProductResponseDto> getStoresByProduct(Long productId);
	
	void updatePriceAndStock(StoreProductRequestDto dto);
	
	

}
