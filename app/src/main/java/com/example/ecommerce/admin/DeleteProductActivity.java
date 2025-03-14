package com.example.ecommerce.admin;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.ItemDomain;
import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteProductActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<ItemDomain> items;
    private DeleteProductAdapter adapter;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_product);

        listView = findViewById(R.id.listViewDeleteProducts);
        mDatabase = FirebaseDatabase.getInstance().getReference("Items");
        items = new ArrayList<>();
        adapter = new DeleteProductAdapter(this, items);
        listView.setAdapter(adapter);

        loadItems();
    }

    private void loadItems() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ItemDomain item = snapshot.getValue(ItemDomain.class);
                    if (item != null) {
                        items.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DeleteProductActivity.this, "Failed to load items.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
