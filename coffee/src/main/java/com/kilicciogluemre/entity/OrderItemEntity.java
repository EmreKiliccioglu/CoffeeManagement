package com.kilicciogluemre.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItemEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_product_id", nullable = true)
    private StoreProductEntity storeProduct;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal priceAtOrderTime;

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public StoreProductEntity getStoreProduct() {
        return storeProduct;
    }

    public void setStoreProduct(StoreProductEntity storeProduct) {
        this.storeProduct = storeProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtOrderTime() {
        return priceAtOrderTime;
    }

    public void setPriceAtOrderTime(BigDecimal priceAtOrderTime) {
        this.priceAtOrderTime = priceAtOrderTime;
    }
}
