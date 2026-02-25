package com.kilicciogluemre.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.kilicciogluemre.GlobalException.ResourceNotFoundException;
import com.kilicciogluemre.Mapper.StoreMapper;
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

	private final StoreRepository storeRepository;
	private final StoreMapper storeMapper;

	public StoreServiceImpl(StoreRepository storeRepository,
							StoreMapper storeMapper) {
		this.storeRepository = storeRepository;
		this.storeMapper = storeMapper;
	}
	
	@Override
	public StoreResponseDto createStore(StoreRequestDto storeRequestDto) {

		StoreEntity store = storeMapper.toEntity(storeRequestDto);
		StoreEntity saved = storeRepository.save(store);

		return storeMapper.toResponse(saved);
	}

	@Override
	public List<StoreResponseDto> getAllStores() {

		return storeRepository.findAll()
				.stream()
				.map(storeMapper::toResponse)
				.toList();
	}

	@Override
	public StoreResponseDto getStoreById(Long id) {

		StoreEntity store = storeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Store Not Found With ID :" +id));

		return  storeMapper.toResponse(store);
	}

	@Override
	public StoreResponseDto updateStore(Long id, StoreRequestDto storeRequestDto) {
		StoreEntity store = storeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Store Not Found With ID :" +id));
		
		if(storeRequestDto.getName() != null) {
			store.setName(storeRequestDto.getName());
		}
		if(storeRequestDto.getAddress() != null) {
			store.setAddress(storeRequestDto.getAddress());
		}
		StoreEntity updatedStore = storeRepository.save(store);
		return storeMapper.toResponse(updatedStore);
	}

	@Override
	public List<StoreResponseDto> searchByName(String name) {
		
		return storeRepository
				.findByNameContainingIgnoreCase(name)
				.stream()
				.map(storeMapper::toResponse)
				.toList();
	}

	@Override
	public List<StoreResponseDto> searchByAddress(String address) {
		return storeRepository
				.findByAddressContainingIgnoreCase(address).stream()
				.map(storeMapper::toResponse)
				.toList();
	}
}
