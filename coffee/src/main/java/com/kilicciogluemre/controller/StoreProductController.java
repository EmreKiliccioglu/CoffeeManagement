package com.kilicciogluemre.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kilicciogluemre.Dto.Request.StoreProductRequestDto;
import com.kilicciogluemre.Dto.Response.StoreProductResponseDto;
import com.kilicciogluemre.service.impl.StoreProductServiceImpl;

@RestController
@RequestMapping("/rest/api/store-products")
public class StoreProductController {
	
    @Autowired
    private StoreProductServiceImpl service;

    @PostMapping("/create")
    public StoreProductResponseDto add(@RequestBody StoreProductRequestDto dto) {
        return service.addProductToStore(dto);
    }
    
    @GetMapping("/store/{storeId}")
    public List<StoreProductResponseDto> getProductByStore(@PathVariable Long storeId){
    	return service.getProductsByStore(storeId);
    }
    
    @GetMapping("/product/{productId}")
    public List<StoreProductResponseDto> getStoresByProduct(@PathVariable Long productId){
    	return service.getStoresByProduct(productId);
    }
    
    @PutMapping("/update")
    public void updatePriceAndStock(@RequestBody StoreProductRequestDto dto) {
    	service.updatePriceAndStock(dto);
    }

}
