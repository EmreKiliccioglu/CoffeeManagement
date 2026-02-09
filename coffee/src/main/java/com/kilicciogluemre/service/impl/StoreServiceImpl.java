package com.kilicciogluemre.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kilicciogluemre.Dto.Request.StoreRequestDto;
import com.kilicciogluemre.Dto.Response.StoreResponseDto;
import com.kilicciogluemre.entity.StoreEntity;
import com.kilicciogluemre.repository.StoreRepository;
import com.kilicciogluemre.service.IStoreService;

@Service
public class StoreServiceImpl implements IStoreService {

	@Autowired
	private StoreRepository storeRepository;
	
	@Override
	public StoreResponseDto createStore(StoreRequestDto storeRequestDto) {
		
		StoreEntity store = new StoreEntity();
		
		store.setName(storeRequestDto.getName());
		store.setAddress(storeRequestDto.getAddress());
		store.setActive(true);
		store.setDeleted(false); 
		
		StoreEntity savedStore = storeRepository.save(store);
		StoreResponseDto responseDto = new StoreResponseDto();
		
		responseDto.setId(savedStore.getId());
		responseDto.setName(savedStore.getName());
		responseDto.setAddress(savedStore.getAddress());
	
		return responseDto;
	}

	@Override
	public List<StoreResponseDto> getAllStores() {
		
		List<StoreEntity> stores = storeRepository.findAll();
		
		return stores.stream().map(store -> {
			StoreResponseDto responseDto = new StoreResponseDto();
			responseDto.setId(store.getId());
			responseDto.setAddress(store.getAddress());
			responseDto.setName(store.getName());
			
			return responseDto;
		
		}).collect(Collectors.toList());
	}

	@Override
	public StoreResponseDto getStoreById(Long id) {
		
		StoreEntity store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Store Not Found With ID :" + id));
		
		StoreResponseDto responseDto = new StoreResponseDto();
		responseDto.setId(store.getId());
		responseDto.setName(store.getName());
		responseDto.setAddress(store.getAddress());
		
		return responseDto;
	}

	@Override
	public StoreResponseDto updateStore(Long id, StoreRequestDto storeRequestDto) {
		StoreEntity store = storeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Store Not Found With ID :" +id));
		
		if(storeRequestDto.getName() != null) {
			store.setName(storeRequestDto.getName());
		}
		if(storeRequestDto.getAddress() != null) {
			store.setAddress(storeRequestDto.getAddress());
		}
		
		StoreEntity updatedStore = storeRepository.save(store);
		
		StoreResponseDto responseDto = new StoreResponseDto();
		responseDto.setId(updatedStore.getId());
		responseDto.setAddress(updatedStore.getAddress());
		responseDto.setName(updatedStore.getName());
		return responseDto;
	}

	@Override
	public List<StoreResponseDto> searchByName(String name) {
		
		return storeRepository.findByNameContainingIgnoreCase(name).stream().map(store -> {
			StoreResponseDto responseDto = new StoreResponseDto();
			BeanUtils.copyProperties(store, responseDto);
			return responseDto;
		}).toList();
	}

	@Override
	public List<StoreResponseDto> searchByAddress(String address) {
		return storeRepository.findByAddressContainingIgnoreCase(address).stream().map(store -> {
			StoreResponseDto responseDto = new StoreResponseDto();
			BeanUtils.copyProperties(store, responseDto);
			return responseDto;
		}).toList();
	}
}
