package com.example.ecommerce.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.ItemDomain;
import com.example.ecommerce.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProductActivity extends AppCompatActivity {

    EditText editTextName, editTextDescription, editTextPrice;
    Button addProductButton;

    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);

        dbReference = FirebaseDatabase.getInstance().getReference("Items"); // Firebase Database

        editTextName = findViewById(R.id.edittextProductName);
        editTextDescription = findViewById(R.id.edittextProductDescrption);
        editTextPrice = findViewById(R.id.edittextProductPrice);
        addProductButton = findViewById(R.id.buttonadd);

        // Add product button click
        addProductButton.setOnClickListener(view -> {
            saveProduct();
        });
    }

    // Save product details to Firebase Database
    private void saveProduct() {
        String name = editTextName.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String priceString = editTextPrice.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || TextUtils.isEmpty(priceString)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = dbReference.push().getKey();
        if (id == null) {
            Toast.makeText(this, "Error generating product ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create item object
        ItemDomain itemDomain = new ItemDomain();
        itemDomain.setId(id);
        itemDomain.setTitle(name);
        itemDomain.setDescription(description);
        itemDomain.setPrice(price);
        itemDomain.setStar(0.0); // Default rating

        // Save to Firebase Database
        dbReference.child(id).setValue(itemDomain)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
                    editTextName.setText("");
                    editTextDescription.setText("");
                    editTextPrice.setText("");
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show());
    }
}
