package com.kilicciogluemre.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kilicciogluemre.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Page<UserEntity> findByDeletedFalseAndActiveTrue(Pageable pageable);
	Page<UserEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
	Optional<UserEntity> findByEmail(String email);
}
