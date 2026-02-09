package com.kilicciogluemre.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kilicciogluemre.entity.StoreProductEntity;

@Repository
public interface StoreProductRepository 
        extends JpaRepository<StoreProductEntity, Long> {

    List<StoreProductEntity> findByStore_Id(Long storeId);
    
    List<StoreProductEntity> findByProduct_Id(Long productId);

    void deleteAllByProduct_Id(Long productId);
    
    Optional<StoreProductEntity> findByStore_IdAndProduct_Id(Long storeId, Long productId);

}
