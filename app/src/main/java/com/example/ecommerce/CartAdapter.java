package com.example.ecommerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private Context context;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);

        // Set item details
        holder.titleTxt.setText(cartItem.getTitle());
        holder.priceTxt.setText("$" + cartItem.getPrice());
        holder.quantityTxt.setText(cartItem.getQuantity() + " kg");

        Glide.with(context)
                .load(cartItem.getImagePath())
                .into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, priceTxt, quantityTxt;
        ImageView itemImage;

        public CartViewHolder(View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.cart_item_title);
            priceTxt = itemView.findViewById(R.id.cart_item_price);
            quantityTxt = itemView.findViewById(R.id.cart_item_quantity);
            itemImage = itemView.findViewById(R.id.cart_item_image);
        }
    }
}
