package com.example.ecommerce.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.MainActivity;
import com.example.ecommerce.R;
import com.example.ecommerce.admin.AdminHomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignInActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private FirebaseAuth auth;
    private Button signInButton, signUpButton;
    private TextView forgotPasswordButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();


        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordTitle);


        if (signInButton != null) {
            signInButton.setOnClickListener(v -> signIn());
        } else {
            Log.e("SignInActivity", "signInButton is null");
        }

        if (signUpButton != null) {
            signUpButton.setOnClickListener(v -> startActivity(new Intent(SignInActivity.this, SignUpActivity.class)));
        } else {
            Log.e("SignInActivity", "signUpButton is null");
        }

        if (forgotPasswordButton != null) {
            forgotPasswordButton.setOnClickListener(v -> startActivity(new Intent(SignInActivity.this, ForgotPassword.class)));
        } else {
            Log.e("SignInActivity", "forgotPasswordButton is null");
        }
    }

    private void signIn() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate email
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email");
            emailEditText.requestFocus();
            return;
        }

        // Validate password
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }
        if (password.length() < 5) {
            passwordEditText.setError("Password must be at least 5 characters");
            passwordEditText.requestFocus();
            return;
        }


        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        if (email.equals("admin@gmail.com")) {

                            startActivity(new Intent(SignInActivity.this, AdminHomeActivity.class));
                        } else {

                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        }
                        finish();
                    } else {

                        String errorMessage;
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            errorMessage = "No account found with this email.";
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            errorMessage = "Invalid password or email.";
                        } catch (FirebaseAuthUserCollisionException e) {
                            errorMessage = "An account already exists with this email.";
                        } catch (Exception e) {
                            errorMessage = "Authentication failed: " + e.getMessage();
                        }
                        Toast.makeText(SignInActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
