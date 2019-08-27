package com.peterholub.store;

import com.peterholub.entity.Product;
import com.peterholub.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStore implements Store {
    private static final String PRODUCT_ID_COLUMN = "productId";
    private static final String PRICE_COLUMN = "price";
    private static final String STATUS_COLUMN = "status";
    private static final String NAME_COLUMN = "name";
    private static final String CATEGORY_ID_COLUMN = "categoryId";
    private static final String STORE_ID_COLUMN = "storeId";

    private static final String GET_ALL_PRODUCTS_QUERY = "Select * from stores.product where storeid=? and categoryId=? order by productId;";
    private static final String ADD_LIST_OF_PRODUCTS_QUERY = "INSERT INTO Stores.Product (price, status, name, categoryId, storeId) VALUES (?, ?, ?, ?, ?);";
    private static final String CHANGE_PRODUCT_STATUS_QUERY = "update Stores.product set status=? where categoryId = ?;";
    private static final String CHANGE_PRODUCT_PRICE_QUERY = "update stores.product set price=price*? where storeId = ? and status=?;";

    /**
     * Method to get all products by store and category
     *
     * @param storeId  Store id in database
     * @param category Category id in database
     * @return List of Products
     */
    @Override
    public List<Product> getAllProductsByStoreAndCategory(int storeId, int category) {
        List<Product> products = new ArrayList<>();
        ResultSet resultSet;
        try (final Connection connection = DatabaseConnection.getDataSource().getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCTS_QUERY)) {

            preparedStatement.setInt(1, storeId);
            preparedStatement.setInt(2, category);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt(PRODUCT_ID_COLUMN));
                product.setPrice(resultSet.getBigDecimal(PRICE_COLUMN));
                product.setStatus(resultSet.getString(STATUS_COLUMN));
                product.setName(resultSet.getString(NAME_COLUMN));
                product.setCategoryId(resultSet.getInt(CATEGORY_ID_COLUMN));
                product.setStoreId(resultSet.getInt(STORE_ID_COLUMN));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Method to add amount of products to database
     *
     * @param products List of products to add into database
     */
    @Override
    public void addListOfProducts(List<Product> products) {
        try (final Connection connection = DatabaseConnection.getDataSource().getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(ADD_LIST_OF_PRODUCTS_QUERY)) {
            for (Product product : products) {
                preparedStatement.setBigDecimal(1, product.getPrice());
                preparedStatement.setString(2, product.getStatus());
                preparedStatement.setString(3, product.getName());
                preparedStatement.setInt(4, product.getCategoryId());
                preparedStatement.setInt(5, product.getStoreId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to change product status by category
     *
     * @param status     Status of product
     * @param categoryId Product category id
     */
    @Override
    public void changeProductStatus(String status, int categoryId) {

        try (final Connection connection = DatabaseConnection.getDataSource().getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PRODUCT_STATUS_QUERY)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to change product price by store and status
     *
     * @param percentage Percentage to increase price
     * @param storeId    Store id
     * @param status     Status of product
     */
    @Override
    public void changeProductPriceByStoreAndStatus(double percentage, int storeId, String status) {

        try (final Connection connection = DatabaseConnection.getDataSource().getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PRODUCT_PRICE_QUERY)) {
            preparedStatement.setDouble(1, percentage);
            preparedStatement.setInt(2, storeId);
            preparedStatement.setString(3, status);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
