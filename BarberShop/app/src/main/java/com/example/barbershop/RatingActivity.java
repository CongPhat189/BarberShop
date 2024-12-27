package com.example.barbershop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RatingActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button btnSubmitRating;
    private RatingBar Rating_Bar;
    private EditText ReviewTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        db = new DatabaseHelper(this);

        Rating_Bar = findViewById(R.id.Rating_Bar);
        ReviewTxt = findViewById(R.id.ReviewTxt);

        btnSubmitRating = findViewById(R.id.btnSubmitRating);
        btnSubmitRating.setOnClickListener(v -> submitRating()
        );
    }
    private void submitRating() {
        SharedPreferences preferencesUser = getSharedPreferences("UserSession", MODE_PRIVATE);
        int user_id = preferencesUser.getInt("user_id", -1);
        SharedPreferences preferencesLocation = getSharedPreferences("LocationSession", MODE_PRIVATE);
        int location_id = preferencesLocation.getInt("location_id", -1);
        float rating = Rating_Bar.getRating();
        String review = ReviewTxt.getText().toString();
        boolean result = db.addRating(user_id, location_id, rating, review);
        if (result) {
            Toast.makeText(RatingActivity.this, "Đánh giá: " + rating + "\nNhận xét: " + review, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RatingActivity.this, BookingActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(RatingActivity.this, "Đánh giá thất bại", Toast.LENGTH_LONG).show();
        }
    }

}



