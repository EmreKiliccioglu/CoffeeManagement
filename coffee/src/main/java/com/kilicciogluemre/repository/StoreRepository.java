package com.kilicciogluemre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kilicciogluemre.entity.StoreEntity;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
	
	List<StoreEntity> findByNameContainingIgnoreCase(String name);
	
	List<StoreEntity> findByAddressContainingIgnoreCase(String address);



}
