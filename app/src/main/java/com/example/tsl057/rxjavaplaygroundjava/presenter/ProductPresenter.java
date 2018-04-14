package com.example.tsl057.rxjavaplaygroundjava.presenter;

import com.example.tsl057.rxjavaplaygroundjava.contracts.ProductListener;
import com.example.tsl057.rxjavaplaygroundjava.models.Product;
import com.example.tsl057.rxjavaplaygroundjava.repository.ProductRepository;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductPresenter {

    private static final String TAG = ProductPresenter.class.getName();
    private final ProductRepository repository;
    private final List<Product> products = new LinkedList<>();

    public ProductPresenter(ProductRepository repository) {
        this.repository = repository;
    }

    public void getAllProducts(ProductListener.AllProducts listener) {
        repository.getProducts().subscribe(products -> listener.onProductsReceived(products));
    }

    //MVP methods
    public void subscribeAllProducts(ProductListener.ModifiedProduct listener) {
        repository
                .getProductObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(product -> listener.onProductModified(product));
    }

    public void subscribeOffers(ProductListener.OfferProduct listener) {
        repository.getProductObservable()
                .filter(product -> product.price < 5)
                .subscribe(product -> listener.onProductOffer(product), throwable -> Observable.just(new Product()));
    }

    //MVVM methods
    public List<Product> getProducts() {
        repository.getProductObservable().subscribe(product -> {
            products.add(product);
        });
        return products;
    }
}
