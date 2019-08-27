package com.peterholub.store;

import com.peterholub.entity.Product;

import java.util.List;

public interface Store {
    /**
     * Method to get all products by store and category
     *
     * @param storeId  Store id in database
     * @param category Category id in database
     * @return List of Products
     */
    List<Product> getAllProductsByStoreAndCategory(int storeId, int category);
    /**
     * Method to add amount of products to database
     *
     * @param products List of products to add into database
     */
    void addListOfProducts(List<Product> products);
    /**
     * Method to change product status by category
     *
     * @param status     Status of product
     * @param categoryId Product category id
     */
    void changeProductStatus(String status, int categoryId);
    /**
     * Method to change product price by store and status
     *
     * @param percentage Percentage to increase price
     * @param storeId    Store id
     * @param status     Status of product
     */
    void changeProductPriceByStoreAndStatus(double percentage, int storeId, String status);
}
