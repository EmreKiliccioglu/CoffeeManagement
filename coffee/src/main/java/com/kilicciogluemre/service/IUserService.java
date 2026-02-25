package com.kilicciogluemre.service;

import java.util.List;

import com.kilicciogluemre.Dto.Request.UserRequestDto;
import com.kilicciogluemre.Dto.Response.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

	UserResponseDto createUser(UserRequestDto userRequestDto);
	
	Page<UserResponseDto> getAllUsers(Pageable pageable);
	
	UserResponseDto getUserById(Long id);
	
	UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);
	
	void deleteUserById(Long id);
	
	Page<UserResponseDto> getActiveUsers(Pageable pageable);
	
	Page<UserResponseDto> searchByName(String name, Pageable pageable);
}
