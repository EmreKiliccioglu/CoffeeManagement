package com.kilicciogluemre.repository;

import java.math.BigDecimal;

public interface TopStoreRevenueProjection {

	Long getStoreId();
	BigDecimal getTotalRevenue();
}
