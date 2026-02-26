package com.kilicciogluemre.service.impl;


import com.kilicciogluemre.GlobalException.Exceptions;
import com.kilicciogluemre.Mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kilicciogluemre.Dto.Request.UserRequestDto;
import com.kilicciogluemre.Dto.Response.UserResponseDto;
import com.kilicciogluemre.entity.Role;
import com.kilicciogluemre.entity.UserEntity;
import com.kilicciogluemre.repository.UserRepository;
import com.kilicciogluemre.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;

	public UserServiceImpl(UserRepository userRepository,
						   PasswordEncoder passwordEncoder,
						   UserMapper userMapper) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
	}
	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		UserEntity user = userMapper.toEntity(userRequestDto);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if(userRequestDto.getRole() != null) {
			user.setRole(userRequestDto.getRole());
		} else {
			user.setRole(Role.USER);
		}
		user.setActive(true);
		user.setDeleted(false);

		UserEntity savedUser = userRepository.save(user);
		return userMapper.toDto(savedUser);
	}

	@Override
	public Page<UserResponseDto> getAllUsers(Pageable pageable) {
		Page<UserEntity> usersPage = userRepository.findAll(pageable);
		return usersPage.map(userMapper::toDto);
	}

	@Override
	public UserResponseDto getUserById(Long id) {
		UserEntity user = userRepository.findById(id)
				.orElseThrow(() -> new Exceptions.ResourceNotFoundException("User Not Found With ID : " + id));

		return userMapper.toDto(user);
	}

	@Override
	public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
		UserEntity user = userRepository.findById(id)
				.orElseThrow(() -> new Exceptions.ResourceNotFoundException("User Not Found With ID :" + id));

		userMapper.updateEntityFromDto(userRequestDto, user);

		UserEntity updatedUser = userRepository.save(user);

		return userMapper.toDto(updatedUser);
	}
	
	@Override
	public void deleteUserById(Long id) {
		UserEntity user = userRepository.findById(id).orElseThrow(() -> new Exceptions.ResourceNotFoundException("User Not Found With ID : " +id));
		
		if(user.isDeleted()) {
			throw new IllegalStateException("User already deleted with id" +id);
		}
		user.setDeleted(true);
		user.setActive(false);
		userRepository.save(user);
	}

	@Override
	public Page<UserResponseDto> getActiveUsers(Pageable pageable) {
		Page<UserEntity> usersPage = userRepository.findByDeletedFalseAndActiveTrue(pageable);
		return usersPage.map(userMapper::toDto);
	}

	@Override
	public Page<UserResponseDto> searchByName(String name, Pageable pageable) {
		Page<UserEntity> usersPage = userRepository.findByNameContainingIgnoreCase(name, pageable);
		return usersPage.map(userMapper::toDto);
	}
}
