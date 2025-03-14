package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private ArrayList<CartItem> cartItems = new ArrayList<>();
    private TextView totalPriceTxt;
    private Button checkoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerViewCart);
        totalPriceTxt = findViewById(R.id.totalPriceTxt);
        checkoutBtn = findViewById(R.id.checkoutBtn);

        // Retrieve CartItem from the Intent
        CartItem cartItem = (CartItem) getIntent().getSerializableExtra("cartItem");

        // Add the CartItem to the cartItems list
        if (cartItem != null) {
            cartItems.add(cartItem);
        }

        // Set up RecyclerView with CartAdapter
        cartAdapter = new CartAdapter(this, cartItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);

        // Calculate and display total price
        updateTotalPrice();

        // Handle Checkout button click
        checkoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, StripeApi.class);
            intent.putExtra("totalPrice", calculateTotalPrice()); // Pass total price
            startActivity(intent);
        });
    }

    private void updateTotalPrice() {
        double totalPrice = calculateTotalPrice();
        totalPriceTxt.setText("Total: $" + totalPrice);
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }
}
