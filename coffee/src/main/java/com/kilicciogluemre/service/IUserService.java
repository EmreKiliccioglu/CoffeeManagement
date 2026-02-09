package com.kilicciogluemre.service;

import java.util.List;

import com.kilicciogluemre.Dto.Request.UserRequestDto;
import com.kilicciogluemre.Dto.Response.UserResponseDto;

public interface IUserService {

	UserResponseDto createUser(UserRequestDto userRequestDto);
	
	List<UserResponseDto> getAllUsers();
	
	UserResponseDto getUserById(Long id);
	
	UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);
	
	void deleteUserById(Long id);
	
	List<UserResponseDto> getActiveUsers();
	
	List<UserResponseDto> searchByName(String name);

}
