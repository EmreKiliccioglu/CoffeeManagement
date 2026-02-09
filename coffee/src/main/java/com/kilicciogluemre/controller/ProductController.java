package com.kilicciogluemre.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kilicciogluemre.Dto.Request.ProductRequestDto;
import com.kilicciogluemre.Dto.Response.ProductResponseDto;
import com.kilicciogluemre.service.IProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/product")
public class ProductController {

	@Autowired
	private IProductService productService;
	
	@PostMapping("/create")
	public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto){
		ProductResponseDto response = productService.createProduct(requestDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<ProductResponseDto>> getAllProduct(){
		return ResponseEntity.ok(productService.getAllProducts()); 
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id){
		productService.deleteProductById(id);
		return ResponseEntity.ok("Product deleted successfully");
	}
	
	@GetMapping("/list/{id}")
	public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id){
		return ResponseEntity.ok(productService.getProductById(id));
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<ProductResponseDto>> searchByName(@RequestParam String name){
		return ResponseEntity.ok(productService.getProductsByName(name));
	}
}
