package com.example.ecommerce.admin;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.ItemDomain;
import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateProductActivity extends AppCompatActivity {

    private ListView productListView;
    private ArrayList<ItemDomain> productList;
    private ProductListAdapter productAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        productListView = findViewById(R.id.listViewProducts);
        databaseReference = FirebaseDatabase.getInstance().getReference("Items");

        productList = new ArrayList<>();
        productAdapter = new ProductListAdapter(this, productList);
        productListView.setAdapter(productAdapter);

        loadProducts();
    }

    private void loadProducts() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ItemDomain item = snapshot.getValue(ItemDomain.class);
                    if (item != null) {
                        productList.add(item);
                    }
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
