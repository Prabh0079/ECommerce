package com.example.ecommerce.auth;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {



        private EditText emailEditText;
        private Button resetPasswordButton;
        private ProgressBar progressBar;
        private FirebaseAuth firebaseAuth;

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_forgot_password);
            firebaseAuth = FirebaseAuth.getInstance();
            emailEditText = findViewById(R.id.emailEditText);
            resetPasswordButton = findViewById(R.id.resetPasswordButton);
            progressBar = findViewById(R.id.progressBar);

            resetPasswordButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendPasswordResetEmail();
                }
            });
        }

        private void sendPasswordResetEmail() {
            String email = emailEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                emailEditText.setError("Email is required!");
                emailEditText.requestFocus();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this,
                                    "An email to reset your password has been sent.",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ForgotPassword.this,
                                    "Error: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }