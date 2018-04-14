package com.example.tsl057.rxjavaplaygroundjava.repository;

import android.util.Log;

import com.example.tsl057.rxjavaplaygroundjava.models.Product;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class ProductRepository {


    private static final String TAG = ProductRepository.class.getName();
    private final CollectionReference collection;

    public ProductRepository(FirebaseFirestore db) {
        collection = db.collection("Products");
    }

    public Single<List<Product>> getProducts() {
        return Single.create(emitter -> collection.get().addOnCompleteListener(task -> {
            if (task.isComplete()) {
                if (!emitter.isDisposed())
                    emitter.onSuccess(task.getResult().toObjects(Product.class));
            }
        }));
    }

    public Observable<Product> getProductObservable() {
        return Observable.create(emitter -> collection.addSnapshotListener(
                (queryDocumentSnapshots, e) -> {
                    for (DocumentChange document : queryDocumentSnapshots.getDocumentChanges()) {
                        if (!emitter.isDisposed()) {
                            Log.d(TAG, document.getDocument().getData().toString());
                            emitter.onNext(document.getDocument().toObject(Product.class));
                        }
                    }
                }
        ));
    }
}
