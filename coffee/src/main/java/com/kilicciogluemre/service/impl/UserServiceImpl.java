package com.kilicciogluemre.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kilicciogluemre.Dto.Request.UserRequestDto;
import com.kilicciogluemre.Dto.Response.UserResponseDto;
import com.kilicciogluemre.entity.Role;
import com.kilicciogluemre.entity.UserEntity;
import com.kilicciogluemre.repository.UserRepository;
import com.kilicciogluemre.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {

		UserEntity user = new UserEntity();

		user.setName(userRequestDto.getName());
		user.setEmail(userRequestDto.getEmail());
		user.setPassword(userRequestDto.getPassword());

		user.setRole(Role.USER);
		user.setActive(true);
		user.setDeleted(false);

		UserEntity savedUser = userRepository.save(user);

		UserResponseDto response = new UserResponseDto();

		response.setId(savedUser.getId());
		response.setName(savedUser.getName());
		response.setEmail(savedUser.getEmail());
		response.setRole(savedUser.getRole());
		response.setActive(savedUser.getActive());

		return response;
	}

	@Override
	public List<UserResponseDto> getAllUsers() {
		List<UserEntity> users = userRepository.findAll();

		return users.stream().map(user -> {
			UserResponseDto responseDto = new UserResponseDto();
			responseDto.setId(user.getId());
			responseDto.setName(user.getName());
			responseDto.setEmail(user.getEmail());
			responseDto.setRole(user.getRole());
			responseDto.setActive(user.getActive());
			
			return responseDto;

		}).collect(Collectors.toList());
	}

	@Override
	public UserResponseDto getUserById(Long id) {
		UserEntity user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User Not Found With ID : " + id));

		UserResponseDto responseDto = new UserResponseDto();
		responseDto.setId(user.getId());
		responseDto.setName(user.getName());
		responseDto.setEmail(user.getEmail());
		responseDto.setRole(user.getRole());
		responseDto.setActive(user.getActive());
		
		return responseDto;
	}

	@Override
	public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
		UserEntity user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User Not Found With ID :" + id));

		if (userRequestDto.getName() != null) {
			user.setName(userRequestDto.getName());
		}
		if (userRequestDto.getEmail() != null) {
			user.setEmail(userRequestDto.getEmail());
		}
		if (userRequestDto.getPassword() != null) {
			user.setPassword(userRequestDto.getPassword());
		}

		UserEntity updatedUser = userRepository.save(user);

		UserResponseDto dto = new UserResponseDto();
		dto.setId(updatedUser.getId());
		dto.setName(updatedUser.getName());
		dto.setEmail(updatedUser.getEmail());
		dto.setRole(updatedUser.getRole());
		dto.setActive(updatedUser.getActive());

		return dto;
	}
	
	@Override
	public void deleteUserById(Long id) {
		UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found With ID : " +id));
		
		if(user.isDeleted()) {
			throw new RuntimeException("User already deleted");
		}
		
		user.setDeleted(true);
		user.setActive(false);
		
		userRepository.save(user);
	}

	@Override
	public List<UserResponseDto> getActiveUsers() {
		
		List<UserEntity> users = userRepository.findByDeletedFalseAndActiveTrue();
		
		return users.stream().map(user -> {
			
			UserResponseDto dto = new UserResponseDto();
			dto.setId(user.getId());
			dto.setName(user.getName());
			dto.setEmail(user.getEmail());
			dto.setRole(user.getRole());
			dto.setActive(user.getActive());
			
			
			return dto;
		}).toList();		
	}
}
