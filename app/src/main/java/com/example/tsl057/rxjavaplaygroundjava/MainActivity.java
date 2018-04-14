package com.example.tsl057.rxjavaplaygroundjava;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.tsl057.rxjavaplaygroundjava.adapter.CardsAdapter;
import com.example.tsl057.rxjavaplaygroundjava.contracts.ProductListener;
import com.example.tsl057.rxjavaplaygroundjava.databinding.ActivityMainBinding;
import com.example.tsl057.rxjavaplaygroundjava.dialogs.CardDialogFragment;
import com.example.tsl057.rxjavaplaygroundjava.models.Product;
import com.example.tsl057.rxjavaplaygroundjava.presenter.ProductPresenter;
import com.example.tsl057.rxjavaplaygroundjava.repository.ProductRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductListener.ModifiedProduct, ProductListener.OfferProduct {

    private static final String TAG = MainActivity.class.getName();
    private ProductPresenter productPresenter;
    private ActivityMainBinding binding;
    private List<Product> listOfProducts = new LinkedList<>();
    private CardsAdapter cardsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Get a DB and create presenter
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ProductRepository repo = new ProductRepository(db);
        productPresenter = new ProductPresenter(repo);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //MVP style
        cardsAdapter = new CardsAdapter(listOfProducts);
        binding.listProducts.setAdapter(cardsAdapter);
        productPresenter.subscribeAllProducts(this);
        productPresenter.subscribeOffers(this);
    }

    @Override
    public void onProductModified(Product product) {
        Log.d(TAG, "Product modified");
        cardsAdapter.productChanged(product);
    }

    @Override
    public void onProductOffer(Product product) {
        Log.d(TAG, "Offer received");
        CardDialogFragment dialogFragment = new CardDialogFragment(product);
        dialogFragment.show(getFragmentManager(), "missiles");
    }
}