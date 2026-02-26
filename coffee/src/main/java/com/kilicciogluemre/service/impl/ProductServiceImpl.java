package com.kilicciogluemre.service.impl;

import java.util.List;

import com.kilicciogluemre.Mapper.ProductMapper;
import org.springframework.stereotype.Service;

import com.kilicciogluemre.Dto.Request.ProductRequestDto;
import com.kilicciogluemre.Dto.Response.ProductResponseDto;
import com.kilicciogluemre.entity.ProductEntity;
import com.kilicciogluemre.repository.ProductRepository;
import com.kilicciogluemre.repository.StoreProductRepository;
import com.kilicciogluemre.service.IProductService;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final StoreProductRepository storeProductRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              StoreProductRepository storeProductRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.storeProductRepository = storeProductRepository;
        this.productMapper = productMapper;
    }
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto dto) {
        ProductEntity product = productMapper.toEntity(dto);
        ProductEntity saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
            .stream()
            .map(productMapper::toDto)
            .toList();
    }

	@Override
	public void deleteProductById(Long id) {
		storeProductRepository.deleteAllByProduct_Id(id);
		productRepository.deleteById(id);
		
	}

	@Override
	public ProductResponseDto getProductById(Long id) {
		
		ProductEntity product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product Not Found With ID :" +id));
        return productMapper.toDto(product);
	}

	@Override
	public List<ProductResponseDto> getProductsByName(String name) {
		
		return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(productMapper::toDto)
                .toList();
	}
}

