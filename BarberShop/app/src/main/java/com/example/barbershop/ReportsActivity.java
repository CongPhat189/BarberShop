package com.example.barbershop;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReportsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitivy_report);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Thống kê người dùng
        TextView totalUsersView = findViewById(R.id.text_total_users);
        totalUsersView.setText("Tổng số người dùng: " + dbHelper.getTotalUsers());

        TextView usersWithBookingsView = findViewById(R.id.text_users_with_bookings);
        usersWithBookingsView.setText("Người dùng đã đặt lịch: " + dbHelper.getUsersWithBookings());

        TextView mostActiveUserView = findViewById(R.id.text_most_active_user);
        mostActiveUserView.setText("Người dùng hoạt động tích cực nhất: " + dbHelper.getMostActiveUser());

        // Thống kê lịch đã đặt
        TextView totalBookingsView = findViewById(R.id.text_total_bookings);
        totalBookingsView.setText("Tổng số lịch đã đặt: " + dbHelper.getTotalBookings());

        // Thống kê doanh thu
        TextView totalRevenueView = findViewById(R.id.text_total_revenue);
        totalRevenueView.setText("Tổng doanh thu: " + dbHelper.getTotalRevenue() + " VNĐ");
    }

}
