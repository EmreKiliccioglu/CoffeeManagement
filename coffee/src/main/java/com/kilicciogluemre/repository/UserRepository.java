package com.kilicciogluemre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kilicciogluemre.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	List<UserEntity> findByDeletedFalseAndActiveTrue();
	List<UserEntity> findByNameContainingIgnoreCase(String name);

}
