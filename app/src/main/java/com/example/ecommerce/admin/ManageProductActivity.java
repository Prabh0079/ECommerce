package com.example.ecommerce.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;

public class ManageProductActivity extends AppCompatActivity {

        private Button btnAddProduct, btnDeleteProduct, btnUpdateProduct, btnViewProducts;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_manage_product);

            // Initialize buttons
            btnAddProduct = findViewById(R.id.btnAddProduct);
            btnDeleteProduct = findViewById(R.id.btnDeleteProduct);
            btnUpdateProduct = findViewById(R.id.btnUpdateProduct);
            btnViewProducts = findViewById(R.id.btnViewProducts);

            // Button click listeners
            btnAddProduct.setOnClickListener(v -> startActivity(new Intent(ManageProductActivity.this, AddProductActivity.class)));

            btnDeleteProduct.setOnClickListener(v -> startActivity(new Intent(ManageProductActivity.this, DeleteProductActivity.class)));

            btnUpdateProduct.setOnClickListener(v -> startActivity(new Intent(ManageProductActivity.this, UpdateProductActivity.class)));

           btnViewProducts.setOnClickListener(v -> startActivity(new Intent(ManageProductActivity.this, ViewProductsActivity.class)));
        }
    }
