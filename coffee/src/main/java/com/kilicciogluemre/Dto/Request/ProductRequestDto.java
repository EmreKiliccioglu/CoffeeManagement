package com.kilicciogluemre.Dto.Request;

import jakarta.validation.constraints.NotBlank;

public class ProductRequestDto {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}