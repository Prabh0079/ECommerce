package com.example.ecommerce.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.ItemDomain;
import com.example.ecommerce.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProductActivity extends AppCompatActivity {

    private EditText editTextProductName, editTextProductDescription, editTextProductPrice;
    private Button buttonSaveProduct;
    private DatabaseReference databaseReference;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        // Initialize views
        editTextProductName = findViewById(R.id.edittextUpdateName);
        editTextProductDescription = findViewById(R.id.edittextUpdateDescription);
        editTextProductPrice = findViewById(R.id.edittextUpdatePrice);
        buttonSaveProduct = findViewById(R.id.buttonsave);

        // Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Items");

        // Get data from Intent
        productId = getIntent().getStringExtra("productId");
        String name = getIntent().getStringExtra("productName");
        String description = getIntent().getStringExtra("productDescription");
        double price = getIntent().getDoubleExtra("productPrice", 0.0);

        // Set data to input fields
        editTextProductName.setText(name);
        editTextProductDescription.setText(description);
        editTextProductPrice.setText(String.valueOf(price));

        // Update product on button click
        buttonSaveProduct.setOnClickListener(v -> updateProduct());
    }

    private void updateProduct() {
        String name = editTextProductName.getText().toString().trim();
        String description = editTextProductDescription.getText().toString().trim();
        String priceStr = editTextProductPrice.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || TextUtils.isEmpty(priceStr)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);

        // Create updated object
        ItemDomain item = new ItemDomain(productId, name, description, price);

        // Update database
        databaseReference.child(productId).setValue(item)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to update product", Toast.LENGTH_SHORT).show());
    }
}
