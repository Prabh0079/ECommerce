package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThankYouActivity extends AppCompatActivity {

    private Button btnOrderAgain;
    private TextView thankYouMessage;
    private Button btnOrderSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        // Initialize the UI components
        thankYouMessage = findViewById(R.id.thankYouMessage);
        btnOrderAgain = findViewById(R.id.btnOrderAgain);
        btnOrderSummary = findViewById(R.id.btnOrderSummary);

        // Display the "Thank You" message
        thankYouMessage.setText("Thank You for Shopping!");

        // Button listener for "Order Again"
        btnOrderAgain.setOnClickListener(view -> {
            // Go back to the MainActivity
            Intent intent = new Intent(ThankYouActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the ThankYouActivity
        });

        // Button listener for "Order Summary"
        btnOrderSummary.setOnClickListener(view -> {
            // Show a toast message when "Order Summary" is clicked
            Toast.makeText(ThankYouActivity.this, "Order Summary clicked", Toast.LENGTH_SHORT).show();

            // You can add your logic here, such as navigating to another screen for the order summary
            // Example: Intent to navigate to OrderSummaryActivity (if it exists)
            Intent intent = new Intent(ThankYouActivity.this, OrderHistoryActivity.class);
            startActivity(intent);
        });
    }
}
