package com.kilicciogluemre.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kilicciogluemre.Dto.Request.ProductRequestDto;
import com.kilicciogluemre.Dto.Response.ProductResponseDto;
import com.kilicciogluemre.entity.ProductEntity;
import com.kilicciogluemre.repository.ProductRepository;
import com.kilicciogluemre.repository.StoreProductRepository;
import com.kilicciogluemre.service.IProductService;

import jakarta.transaction.Transactional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private StoreProductRepository storeProductRepository;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto dto) {

        ProductEntity product = new ProductEntity();
        product.setName(dto.getName());
        product.setActive(true);
        product.setDeleted(false);

        ProductEntity saved = productRepository.save(product);

        ProductResponseDto response = new ProductResponseDto();
        BeanUtils.copyProperties(saved, response);
        return response;
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
            .stream()
            .map(p -> {
                ProductResponseDto dto = new ProductResponseDto();
                BeanUtils.copyProperties(p, dto);
                return dto;
            })
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
		
		ProductResponseDto responseDto = new ProductResponseDto();
		BeanUtils.copyProperties(product, responseDto);
		return responseDto;
	}

	@Override
	public List<ProductResponseDto> getProductsByName(String name) {
		
		return productRepository.findByNameContainingIgnoreCase(name).stream().map(p -> {
			ProductResponseDto responseDto = new ProductResponseDto();
			BeanUtils.copyProperties(p, responseDto);
			return responseDto;
		}).toList();
	}
}

