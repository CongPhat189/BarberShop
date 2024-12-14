package com.example.barbershop;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);

        recyclerView = findViewById(R.id.recycler_view_bookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        // Lấy user_id từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int user_id = preferences.getInt("user_id", -1);


        // Lấy danh sách lịch
       
        List<Booking> bookingList =  dbHelper.getBookingsByUserId(user_id);

        // Hiển thị trong RecyclerView
        BookingAdapter adapter = new BookingAdapter(this, bookingList);
        recyclerView.setAdapter(adapter);
    }
}
