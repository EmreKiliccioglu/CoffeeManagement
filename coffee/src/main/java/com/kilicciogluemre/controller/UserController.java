package com.kilicciogluemre.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kilicciogluemre.Dto.Request.UserRequestDto;
import com.kilicciogluemre.Dto.Response.UserResponseDto;
import com.kilicciogluemre.service.IUserService;
// Pageable için page ve size parametreleri ile request atılmalı
@RestController
@RequestMapping("/rest/api/users")
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping("/create")
	public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
		UserResponseDto response = userService.createUser(userRequestDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Page<UserResponseDto>> getAllUsers(Pageable pageable) {
		return ResponseEntity.ok(userService.getAllUsers(pageable));
	}

	@GetMapping("/list/{id}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
		UserResponseDto user = userService.getUserById(id);
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<UserResponseDto> updateUser(
	        @PathVariable Long id,
	        @RequestBody UserRequestDto userRequestDto) {

	    UserResponseDto updatedUser = userService.updateUser(id, userRequestDto);
	    return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		userService.deleteUserById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/active")
	public ResponseEntity<Page<UserResponseDto>> getActiveUsers(Pageable pageable) {
		return ResponseEntity.ok(userService.getActiveUsers(pageable));
	}

	@GetMapping("/search")
	public ResponseEntity<Page<UserResponseDto>> searchUsersByName(
			@RequestParam String name,
			Pageable pageable) {

		return ResponseEntity.ok(userService.searchByName(name, pageable));
	}
}