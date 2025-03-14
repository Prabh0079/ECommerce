package com.example.ecommerce.admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.ecommerce.R;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button btnManageProducts = findViewById(R.id.btn_manage_products);
        Button btnManageUsers = findViewById(R.id.btn_manage_users);

        btnManageProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, ManageProductActivity.class);
                startActivity(intent);
            }
        });

        btnManageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, ManageUserActivity.class);
                startActivity(intent);
            }
        });
    }
}
