package com.example.ecommerce.admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.ItemDomain;
import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProductsActivity extends AppCompatActivity {

    private ListView mListView;
    private DatabaseReference mDatabaseReference;
    private ArrayList<String> productList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);

        // Initialize UI components
        mListView = findViewById(R.id.listviewProduct);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Items"); // Corrected to match other files

        productList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        mListView.setAdapter(adapter);

        loadProducts();
    }

    private void loadProducts() {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ItemDomain item = dataSnapshot.getValue(ItemDomain.class);
                    if (item != null) {
                        productList.add(item.getTitle() + " - $" + item.getPrice());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Log error or show toast
            }
        });
    }
}
