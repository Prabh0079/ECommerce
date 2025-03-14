package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;

import com.example.ecommerce.auth.SignInActivity;
import com.example.ecommerce.auth.SignUpActivity;
import com.example.ecommerce.databinding.ActivityIntroBinding;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    private Button startBtn;
    private Button signupBtn;
    private TextView textView;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro); // Reference to the layout file

        // Initialize Views
        startBtn = findViewById(R.id.startBtn);
        signupBtn = findViewById(R.id.signupBtn);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        // Set OnClickListener for Start Button (Sign In)
        startBtn.setOnClickListener(view -> {
            // Navigate to the Sign-In activity
            Intent intent = new Intent(IntroActivity.this, SignInActivity.class); // Replace with your login activity
            startActivity(intent);
            finish(); // Close this activity when navigating
        });

        // Set OnClickListener for SignUp Button
        signupBtn.setOnClickListener(view -> {
            // Navigate to the Sign-Up activity
            Intent intent = new Intent(IntroActivity.this, SignUpActivity.class); // Replace with your sign-up activity
            startActivity(intent);
            finish(); // Close this activity when navigating
        });
    }
}
