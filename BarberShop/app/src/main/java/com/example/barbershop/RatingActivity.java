package com.example.barbershop;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RatingActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText etRating;
    Button btnSubmitRating;
    private RatingBar Rating_Bar;
    private EditText ReviewTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_rating);
//
//        db = new DatabaseHelper(this);
//
//        etRating = findViewById(R.id.etRating);
//        btnSubmitRating = findViewById(R.id.btnSubmitRating);
//
//        btnSubmitRating.setOnClickListener(v -> {
//            int rating = Integer.parseInt(etRating.getText().toString());
//
//            if (rating >= 1 && rating <= 5) {
//                boolean isSubmitted = db.submitRating(1, rating); // Giả sử user_id = 1
//
//                if (isSubmitted) {
//                    Toast.makeText(this, "Đánh giá thành công!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "Lỗi khi gửi đánh giá.", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(this, "Vui lòng nhập đánh giá từ 1 đến 5.", Toast.LENGTH_SHORT).show();
//            }
//        });
        setContentView(R.layout.activity_rating);

        Rating_Bar = findViewById(R.id.Rating_Bar);
        ReviewTxt = findViewById(R.id.ReviewTxt);

        btnSubmitRating = findViewById(R.id.btnSubmitRating);
        btnSubmitRating.setOnClickListener(v ->
        {
            float rating = Rating_Bar.getRating();
            String review = ReviewTxt.getText().toString();
            Toast.makeText(RatingActivity.this, "Đánh giá: " + rating + "\nNhận xét: " + review, Toast.LENGTH_LONG).show();
        });
    }

}



