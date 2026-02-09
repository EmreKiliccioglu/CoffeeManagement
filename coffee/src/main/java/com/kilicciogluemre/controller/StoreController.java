package com.kilicciogluemre.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kilicciogluemre.Dto.Request.StoreRequestDto;
import com.kilicciogluemre.Dto.Response.StoreResponseDto;
import com.kilicciogluemre.service.IStoreService;

@RestController
@RequestMapping("/rest/api/stores")
public class StoreController {
	
	@Autowired
	private IStoreService storeService;
	
	@PostMapping("/create")
	public ResponseEntity<StoreResponseDto> createStore(@RequestBody StoreRequestDto storeRequestDto){
		StoreResponseDto responseDto = storeService.createStore(storeRequestDto);
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<StoreResponseDto>> getAllStores(){
		List<StoreResponseDto> stores = storeService.getAllStores();
		return ResponseEntity.ok(stores);
	}
	
	@GetMapping("/list/{id}")
	public ResponseEntity<StoreResponseDto> getStoreById(@PathVariable Long id){
		StoreResponseDto store = storeService.getStoreById(id);
		return ResponseEntity.ok(store);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<StoreResponseDto> updateStore(
			@PathVariable Long id,
			@RequestBody StoreRequestDto storeRequestDto) {
		StoreResponseDto updatedStore = storeService.updateStore(id, storeRequestDto);
		return ResponseEntity.ok(updatedStore);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<StoreResponseDto>> searchByName(@RequestParam String name){
		return ResponseEntity.ok(storeService.searchByName(name));
	}
	
	@GetMapping("/search/address")
	public ResponseEntity<List<StoreResponseDto>> searchByAddress(@RequestParam String address){
		return ResponseEntity.ok(storeService.searchByAddress(address));
	}

}
