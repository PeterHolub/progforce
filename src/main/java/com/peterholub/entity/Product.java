package com.peterholub.entity;

import java.math.BigDecimal;

public class Product {
    private int productId;
    private BigDecimal price;
    private String status;
    private String name;
    private int categoryId;
    private int storeId;

    public Product() {
    }

    public Product(BigDecimal price, String status, String name, int categoryId, int storeId) {
        this.price = price;
        this.status = status;
        this.name = name;
        this.categoryId = categoryId;
        this.storeId = storeId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
