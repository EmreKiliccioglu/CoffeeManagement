package com.kilicciogluemre.Mapper;

import com.kilicciogluemre.Dto.Request.UserRequestDto;
import com.kilicciogluemre.Dto.Response.UserResponseDto;
import com.kilicciogluemre.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDto toDto(UserEntity user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setActive(user.getActive());
        return dto;
    }

    public UserEntity toEntity(UserRequestDto dto){
        UserEntity user = new UserEntity();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public void updateEntityFromDto(UserRequestDto dto, UserEntity user) {
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
    }
}
