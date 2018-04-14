package com.example.tsl057.rxjavaplaygroundjava.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.tsl057.rxjavaplaygroundjava.R;
import com.example.tsl057.rxjavaplaygroundjava.databinding.CardItemBinding;
import com.example.tsl057.rxjavaplaygroundjava.models.Product;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder> {
    private List<Product> productList;

    public CardsAdapter(List<Product> products) {
        this.productList = products;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Product currentTalk = productList.get(position);
        holder.getBinding().setModel(currentTalk);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void productChanged(Product modifiedProduct) {
        Optional<Product> found = productList
                .stream()
                .filter(product -> (product.id.equalsIgnoreCase(modifiedProduct.id)))
                .findAny();

        if (!found.isPresent()) {
            productList.add(modifiedProduct);
            notifyItemInserted(productList.size());
        } else {
            OptionalInt position = IntStream.range(0, productList.size())
                            .filter(i -> (productList.get(i).id.equalsIgnoreCase(modifiedProduct.id)))
                            .findFirst();
            found.get().updateWith(modifiedProduct);
            notifyItemChanged(position.getAsInt());
        }
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        private CardItemBinding binding;

        CardViewHolder(final CardItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        CardItemBinding getBinding() {
            return binding;
        }
    }
}