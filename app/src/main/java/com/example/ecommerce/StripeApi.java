package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StripeApi extends AppCompatActivity {

    private Button payment;
    private EditText amountEditText;
    private String PublishableKey = "pk_test_51R2CBWLeKuXjzJdlWIHV39uLOsxH0pfjYGIFacw4twgTwf3KmTcne5KzGpJqj8dx0WT7R5jg41AF8Olm6tyVeUiD00ZMSze8wv";
    private String SecretKey = "sk_test_51R2CBWLeKuXjzJdlhm5nhymEKN4NtULB47zpCiRzg4cAn8TbNJIAsYOt20eBI5clqJ04OMXx6Nw4wcBbsruXLZPr00P4ri1mK8";

    private String CustomersURL = "https://api.stripe.com/v1/customers";
    private String EphericalKeyURL = "https://api.stripe.com/v1/ephemeral_keys";
    private String ClientSecretURL = "https://api.stripe.com/v1/payment_intents";

    private String CustomerId = null;
    private String EphericalKey;
    private String ClientSecret;
    private PaymentSheet paymentSheet;

    private int amountInCents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_api);

        payment = findViewById(R.id.payment);
        amountEditText = findViewById(R.id.amountEditText);

        PaymentConfiguration.init(this, PublishableKey);
        paymentSheet = new PaymentSheet(this, this::onPaymentResult);

        // Retrieve total price from intent and set the amount
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);
        amountEditText.setText(String.valueOf(totalPrice)); // Display total price
        amountInCents = (int) (totalPrice * 100); // Convert to cents

        // Disable user input in amountEditText
        amountEditText.setFocusable(false);
        amountEditText.setClickable(false);
        amountEditText.setLongClickable(false);
        amountEditText.setCursorVisible(false);

        payment.setOnClickListener(view -> {
            if (CustomerId != null && !CustomerId.isEmpty()) {
                createPaymentIntent(amountInCents);
            } else {
                Toast.makeText(this, "Customer ID is not available", Toast.LENGTH_SHORT).show();
            }
        });

        createCustomer();
    }

    private void createCustomer() {
        StringRequest request = new StringRequest(Request.Method.POST, CustomersURL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                CustomerId = object.getString("id");
                Log.d("Stripe", "Customer created: " + CustomerId);
                if (CustomerId != null && !CustomerId.isEmpty()) {
                    getEphericalKey();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(StripeApi.this, "Error creating customer: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show()) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SecretKey);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void getEphericalKey() {
        StringRequest request = new StringRequest(Request.Method.POST, EphericalKeyURL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                EphericalKey = object.getString("id");
                if (EphericalKey != null && !EphericalKey.isEmpty()) {
                    getClientSecret(CustomerId, EphericalKey);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(StripeApi.this, "Error fetching ephemeral key: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show()) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SecretKey);
                headers.put("Stripe-Version", "2022-11-15");
                return headers;
            }

            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", CustomerId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void getClientSecret(String customerId, String ephemeralKey) {
        StringRequest request = new StringRequest(Request.Method.POST, ClientSecretURL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                ClientSecret = object.getString("client_secret");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(StripeApi.this, "Error fetching client secret: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show()) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SecretKey);
                return headers;
            }

            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerId);
                params.put("amount", String.valueOf(amountInCents));
                params.put("currency", "usd");
                params.put("automatic_payment_methods[enabled]", "true");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void createPaymentIntent(int amountInCents) {
        if (ClientSecret != null && !ClientSecret.isEmpty()) {
            paymentSheet.presentWithPaymentIntent(ClientSecret, new PaymentSheet.Configuration("Stripe",
                    new PaymentSheet.CustomerConfiguration(CustomerId, EphericalKey)));
        } else {
            Toast.makeText(this, "Client Secret not available", Toast.LENGTH_SHORT).show();
        }
    }



    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();

            // Generate an order ID (e.g., using timestamp)
            String orderId = "order_" + System.currentTimeMillis();
            String customerId = CustomerId; // You already have the customerId from Stripe
            double totalPrice = amountInCents / 100.0; // Convert to dollars

            // Create an Order object
            Order order = new Order(orderId, customerId, totalPrice);

            // Store the order in Firebase Realtime Database
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orders");
            databaseReference.child(orderId).setValue(order)
                    .addOnSuccessListener(aVoid -> {
                        // Order successfully saved in Firebase
                        Toast.makeText(StripeApi.this, "Order stored successfully", Toast.LENGTH_SHORT).show();

                        // Navigate to Thank You Activity after successful payment
                        Intent intent = new Intent(StripeApi.this, ThankYouActivity.class);
                        startActivity(intent);
                        finish(); // Close the StripeApi activity
                    })
                    .addOnFailureListener(e -> {
                        // Handle any errors during the Firebase transaction
                        Toast.makeText(StripeApi.this, "Failed to store order: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
        }
    }

}
