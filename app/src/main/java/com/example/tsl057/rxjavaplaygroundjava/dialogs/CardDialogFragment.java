package com.example.tsl057.rxjavaplaygroundjava.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.tsl057.rxjavaplaygroundjava.R;
import com.example.tsl057.rxjavaplaygroundjava.databinding.CardDialogBinding;
import com.example.tsl057.rxjavaplaygroundjava.models.Product;

public class CardDialogFragment extends DialogFragment {

    private Product model;

    @SuppressLint("ValidFragment")
    public CardDialogFragment (Product model) {
        this.model = model;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        CardDialogBinding binding = DataBindingUtil.inflate(inflater, R.layout.card_dialog, null, false);
        binding.setModel(model);
        builder.setView(binding.getRoot())
                .setTitle(R.string.offer)
                .setPositiveButton(R.string.ok, (dialog, id) -> dialog.dismiss());
        return builder.create();
    }
}