package com.example.barbershop;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ManageAppointmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);

        recyclerView = findViewById(R.id.recycler_view_bookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        // Lấy danh sách lịch
        List<Booking> bookingList = dbHelper.getAllBookings();

        // Hiển thị trong RecyclerView
        AdminBookingAdapter adapter = new AdminBookingAdapter(this, bookingList);
        recyclerView.setAdapter(adapter);
    }
}

