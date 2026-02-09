package com.kilicciogluemre.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class ProductEntity extends BaseEntity{

	@Column(nullable = false)
    private String name;

	@OneToMany(mappedBy = "product")
	private List<StoreProductEntity> storeProducts;

    @Column(nullable = false)
    private Boolean active = true;

    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public Boolean getActive() {
        return active;
    }
 
    public void setActive(Boolean active) {
        this.active = active;
    }
}
