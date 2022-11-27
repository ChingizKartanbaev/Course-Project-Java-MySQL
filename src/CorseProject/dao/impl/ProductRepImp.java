package CorseProject.dao.impl;

import CorseProject.dao.Const;
import CorseProject.dao.DbHelper;
import CorseProject.dao.ProductRep;
import CorseProject.models.Product;
import CorseProject.models.enums.Categories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepImp implements ProductRep {

    DbHelper dbHelper = DbHelper.INSTANCE;


    //TODO поменять select
    @Override
    public List<Product> getAllProduct() {

        List<Product> productList = new ArrayList<>();
        String select = "SELECT * FROM products";

        try (PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Product product = new Product();
                product.setIdProduct(resultSet.getLong("idProducts"));
                product.setName(resultSet.getNString("name"));
                product.setCost(resultSet.getDouble("cost"));
                product.setCategories(Categories.valueOf(resultSet.getString("categories")));
                productList.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    @Override
    public Product getProductById(long id) {

        String select = "SELECT * FROM " + Const.PRODUCTS_TABLE + " WHERE " + Const.PRODUCT_ID + "=?";

        try {
            PreparedStatement preparedStatement = dbHelper.dbGetConnection().prepareStatement(select);
            preparedStatement.setInt(1, Math.toIntExact(id));

            ResultSet resultSet = preparedStatement.executeQuery();
            Product product = new Product();

            while (resultSet.next()){
                product.setIdProduct(resultSet.getLong("idProducts"));
                product.setName(resultSet.getNString("name"));
                product.setCost(resultSet.getDouble("cost"));
                product.setCategories(Categories.valueOf(resultSet.getString("categories")));
            }
            return product;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
