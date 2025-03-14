package com.example.ecommerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.databinding.ViewholderSimilarBinding;



import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.databinding.ViewholderSimilarBinding;

import java.util.ArrayList;

public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.Viewholder> {
    private ArrayList<ItemDomain> items;
    private Context context;

    public SimilarAdapter(ArrayList<ItemDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public SimilarAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderSimilarBinding binding = ViewholderSimilarBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarAdapter.Viewholder holder, int position) {
        ItemDomain currentItem = items.get(position);

        // Load image using Glide
        Glide.with(context)
                .load(currentItem.getImagePath())
                .into(holder.binding.img);

        // Bind title, price, and rating
        holder.binding.titleTxt.setText(currentItem.getTitle());
        holder.binding.priceTxt.setText(currentItem.getPrice() + " $/Kg");
        holder.binding.ratingBar.setRating((float) currentItem.getStar());
        holder.binding.ratingTxt.setText("(" + currentItem.getStar() + ")");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderSimilarBinding binding;

        public Viewholder(ViewholderSimilarBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
