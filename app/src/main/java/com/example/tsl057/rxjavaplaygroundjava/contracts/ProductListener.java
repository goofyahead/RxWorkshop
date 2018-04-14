package com.example.tsl057.rxjavaplaygroundjava.contracts;

import com.example.tsl057.rxjavaplaygroundjava.models.Product;

import java.util.List;

public interface ProductListener {

    interface ModifiedProduct {
        void onProductModified(Product product);
    }

    interface OfferProduct {
        void onProductOffer(Product product);
    }

    interface AllProducts {
        void onProductsReceived(List<Product> products);
    }
}
