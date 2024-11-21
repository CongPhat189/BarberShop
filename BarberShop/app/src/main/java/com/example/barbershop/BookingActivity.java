package com.example.barbershop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.DatabaseHelper;

public class BookingActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText etDateTime;
    Button btnBookAppointment;
    TextView tvBookingStatus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        db = new DatabaseHelper(this);

        etDateTime = findViewById(R.id.etDateTime);
        btnBookAppointment = findViewById(R.id.btnBookAppointment);
        tvBookingStatus = findViewById(R.id.tvBookingStatus);

        btnBookAppointment.setOnClickListener(v -> {
            String datetime = etDateTime.getText().toString();
            boolean isBooked = db.bookAppointment(1, datetime); // Giả sử user_id = 1

            if (isBooked) {
                tvBookingStatus.setText("Đặt lịch thành công!");
            } else {
                tvBookingStatus.setText("Không thể đặt lịch. Thử lại sau.");
            }
            startActivity(new Intent(BookingActivity.this, RatingActivity.class));
        });
    }
}

