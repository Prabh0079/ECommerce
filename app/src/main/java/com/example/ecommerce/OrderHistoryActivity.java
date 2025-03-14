package com.example.ecommerce;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private ArrayList<Order> orderList;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // Initialize RecyclerView and set LayoutManager
        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the order list and adapter
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList);
        recyclerView.setAdapter(orderAdapter);

        // Fetch order details from Firebase
        fetchOrderDetails();
    }

    // Fetch order details from Firebase Realtime Database
    private void fetchOrderDetails() {
        DatabaseReference ordersRef = database.getReference("orders"); // Reference to the "orders" node in Firebase

        // Listen for data changes in the "orders" node
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    orderList.clear(); // Clear previous data in the list
                    for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                        // Convert snapshot to Order object
                        Order order = orderSnapshot.getValue(Order.class);
                        if (order != null) {
                            orderList.add(order); // Add order to the list
                        }
                    }
                    orderAdapter.notifyDataSetChanged(); // Notify adapter to update the RecyclerView with new data
                } else {
                    Toast.makeText(OrderHistoryActivity.this, "No orders found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any error
                Toast.makeText(OrderHistoryActivity.this, "Failed to load orders: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
