package com.kilicciogluemre.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.kilicciogluemre.GlobalException.Exceptions;
import com.kilicciogluemre.Mapper.StoreProductMapper;
import org.springframework.stereotype.Service;

import com.kilicciogluemre.Dto.Request.StoreProductRequestDto;
import com.kilicciogluemre.Dto.Response.StoreProductResponseDto;
import com.kilicciogluemre.entity.ProductEntity;
import com.kilicciogluemre.entity.StoreEntity;
import com.kilicciogluemre.entity.StoreProductEntity;
import com.kilicciogluemre.repository.ProductRepository;
import com.kilicciogluemre.repository.StoreProductRepository;
import com.kilicciogluemre.repository.StoreRepository;
import com.kilicciogluemre.service.IStoreProductService;

import jakarta.transaction.Transactional;

@Service
public class StoreProductServiceImpl implements IStoreProductService {

	private final StoreRepository storeRepository;
	private final ProductRepository productRepository;
	private final StoreProductRepository storeProductRepository;
	private final StoreProductMapper storeProductMapper;

	public StoreProductServiceImpl(StoreRepository storeRepository,
							StoreProductMapper storeProductMapper,
							ProductRepository productRepository,
							StoreProductRepository storeProductRepository) {
		this.storeRepository = storeRepository;
		this.storeProductMapper = storeProductMapper;
		this.productRepository = productRepository;
		this.storeProductRepository = storeProductRepository;
	}

    @Transactional
    public StoreProductResponseDto addProductToStore(StoreProductRequestDto dto) {

        StoreEntity store = storeRepository.findById(dto.getStoreId())
            .orElseThrow(() -> new Exceptions.ResourceNotFoundException("Store not found"));

        ProductEntity product = productRepository.findById(dto.getProductId())
            .orElseThrow(() -> new Exceptions.ResourceNotFoundException("Product not found"));

        StoreProductEntity sp = new StoreProductEntity();
        sp.setStore(store);
        sp.setProduct(product);
        sp.setPrice(dto.getPrice());
        sp.setStock(dto.getStock());
        sp.setActive(true);
        sp.setDeleted(false);

        StoreProductEntity saved = storeProductRepository.save(sp);
		return  storeProductMapper.toDto(saved);
    }
    
    @Transactional
    public List<ProductEntity> getProductsByStoreId(Long storeId) {
        return storeProductRepository.findByStore_Id(storeId)
        		
                .stream()
                .map(StoreProductEntity::getProduct)
                .toList();
    }

	@Override
	public List<StoreProductResponseDto> getProductsByStore(Long storeId) {
		
		storeRepository.findById(storeId).orElseThrow(() -> new Exceptions.ResourceNotFoundException("Store Not Found"));

		return storeProductRepository.findByStore_Id(storeId)
				.stream()
				.map(storeProductMapper::toDto)
				.toList();
	}

	@Override
	public List<StoreProductResponseDto> getStoresByProduct(Long productId) {
		
		productRepository.findById(productId).orElseThrow(() -> new Exceptions.ResourceNotFoundException("Product Not Found"));
		
		List<StoreProductEntity> storeProducts = storeProductRepository.findByProduct_Id(productId);
		
		return storeProducts
				.stream()
				.map(storeProductMapper::toDto)
				.toList();
	}

	@Override
	public void updatePriceAndStock(StoreProductRequestDto dto) {
		
		StoreProductEntity storeProduct = storeProductRepository.
				findByStore_IdAndProduct_Id(dto.getStoreId(), dto.getProductId())
				.orElseThrow(() -> new Exceptions.ResourceNotFoundException("Product Not Found in This Store"));
		
		if(dto.getPrice() != null) {
			if(dto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
				throw new Exceptions.InactiveProductException("Price cannot be negative");
			}
			storeProduct.setPrice(dto.getPrice());
		}
		
		if(dto.getStock() != null) {
			if(dto.getStock() < 0) {
				throw new Exceptions.InvalidDataException("Stock cannot be negative");
			}
			storeProduct.setStock(dto.getStock());
		}
		storeProductRepository.save(storeProduct);
	}
}
