package CorseProject.models;

import CorseProject.models.enums.Categories;

public  class Product {

    private long idProduct;
    private String name;
    private double cost;
    private Categories categories;

    public Product() {
    }

    public Product(String name, double cost, Categories categories) {
        this.name = name;
        this.cost = cost;
        this.categories = categories;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }
}