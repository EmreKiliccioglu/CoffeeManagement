package com.kilicciogluemre.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreProductRepository storeProductRepository;

    @Transactional
    public StoreProductResponseDto addProductToStore(StoreProductRequestDto dto) {

        StoreEntity store = storeRepository.findById(dto.getStoreId())
            .orElseThrow(() -> new RuntimeException("Store not found"));

        ProductEntity product = productRepository.findById(dto.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));

        StoreProductEntity sp = new StoreProductEntity();
        sp.setStore(store);
        sp.setProduct(product);
        sp.setPrice(dto.getPrice());
        sp.setStock(dto.getStock());
        sp.setActive(true);
        sp.setDeleted(false);

        StoreProductEntity saved = storeProductRepository.save(sp);

        StoreProductResponseDto response = new StoreProductResponseDto();
        response.setId(saved.getId());
        response.setStoreId(store.getId());
        response.setProductId(product.getId());
        response.setProductName(product.getName());
        response.setPrice(saved.getPrice());
        response.setStock(saved.getStock());
        response.setActive(saved.getActive());

        return response;
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
		
		storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Store Not Found"));
		
		List<StoreProductEntity> storeProducts = storeProductRepository.findByStore_Id(storeId);
		
		return storeProducts.stream().map(sp -> {
			StoreProductResponseDto responseDto = new StoreProductResponseDto();
			
			responseDto.setId(sp.getId());
			responseDto.setStoreId(sp.getStore().getId());
			responseDto.setProductId(sp.getProduct().getId());
			responseDto.setProductName(sp.getProduct().getName());
			responseDto.setPrice(sp.getPrice());
			responseDto.setStock(sp.getStock());
			responseDto.setActive(sp.getActive());
			
			return responseDto;
		}).toList();
	}

	@Override
	public List<StoreProductResponseDto> getStoresByProduct(Long productId) {
		
		productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product Not Found"));
		
		List<StoreProductEntity> storeProducts = storeProductRepository.findByProduct_Id(productId);
		
		return storeProducts.stream().map(sp -> {
			StoreProductResponseDto responseDto = new StoreProductResponseDto();
			
			BeanUtils.copyProperties(sp, responseDto);
			
			responseDto.setStoreId(sp.getStore().getId());
			responseDto.setStoreName(sp.getStore().getName());
			responseDto.setProductId(sp.getProduct().getId());
			responseDto.setProductName(sp.getProduct().getName());
			
			return responseDto;
			
		}).toList();
	}

	@Override
	public void updatePriceAndStock(StoreProductRequestDto dto) {
		
		StoreProductEntity storeProduct = storeProductRepository.findByStore_IdAndProduct_Id(dto.getStoreId(), dto.getProductId()).orElseThrow(() -> new RuntimeException("Product Not Found in This Store"));
		
		if(dto.getPrice() != null) {
			if(dto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
				throw new RuntimeException("Price cannot be negative");
			}
			storeProduct.setPrice(dto.getPrice());
		}
		
		if(dto.getStock() != null) {
			if(dto.getStock() < 0) {
				throw new RuntimeException("Stock cannot be negative");
			}
			storeProduct.setStock(dto.getStock());
		}
		storeProductRepository.save(storeProduct);
	}
}
