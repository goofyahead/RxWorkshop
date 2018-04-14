package com.example.tsl057.rxjavaplaygroundjava.models;

public class Product {
    public String id;
    public String name;
    public Integer price;
    public String picture;
    public Integer quantity;
    public Integer likes;

    public void updateWith(Product modifiedProduct) {
        this.id = modifiedProduct.id;
        this.name = modifiedProduct.name;
        this.price = modifiedProduct.price;
        this.quantity = modifiedProduct.quantity;
        this.likes = modifiedProduct.likes;
    }
}
