package com.kilicciogluemre.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kilicciogluemre.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	
	@Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM OrderEntity o")
	BigDecimal getTotalRevenue();
	
	@Query("""
	        SELECT COALESCE(SUM(o.totalPrice), 0)
	        FROM OrderEntity o
	        WHERE o.store.id = :storeId
	    """)
	BigDecimal getTotalRevenueByStoreId(Long storeId);
	
    @Query("""
            SELECT 
                o.store.id as storeId,
                SUM(o.totalPrice) as totalRevenue
            FROM OrderEntity o
            GROUP BY o.store.id
            ORDER BY SUM(o.totalPrice) DESC
        """)
        List<TopStoreRevenueProjection> findStoreRevenuesDesc();
}
