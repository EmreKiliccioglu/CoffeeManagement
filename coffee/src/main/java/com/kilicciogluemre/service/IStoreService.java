package com.kilicciogluemre.service;

import java.util.List;

import com.kilicciogluemre.Dto.Request.StoreRequestDto;
import com.kilicciogluemre.Dto.Response.StoreResponseDto;

public interface IStoreService {

	StoreResponseDto createStore(StoreRequestDto storeRequestDto);
	
	List<StoreResponseDto> getAllStores();
	
	StoreResponseDto getStoreById(Long id);
	
	StoreResponseDto updateStore(Long id, StoreRequestDto storeRequestDto);
	
	List<StoreResponseDto> searchByName(String name);
	
	List<StoreResponseDto> searchByAddress(String address);

	
}
