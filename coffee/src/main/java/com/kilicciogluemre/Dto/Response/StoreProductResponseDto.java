package com.kilicciogluemre.Dto.Response;

import java.math.BigDecimal;

public class StoreProductResponseDto {

    private Long id;              
    private Long storeId;
    private Long productId;
    private String storeName;
    private String productName;
    private BigDecimal price;
    private Integer stock;
    private Boolean active;

    public StoreProductResponseDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public String getStoreName() {
    	return storeName;
    }
    
    public void setStoreName(String storeName) {
    	this.storeName = storeName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
