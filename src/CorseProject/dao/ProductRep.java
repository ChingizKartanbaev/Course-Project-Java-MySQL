package CorseProject.dao;

import CorseProject.models.Product;

import java.util.List;

public interface ProductRep {

    List<Product> getAllProduct();
    Product getProductById (long id);

}
