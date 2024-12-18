package com.example.barbershop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    private Button btnManageUsers,btnManageBarbers, btnManageStores, btnManageAppointments, btnViewReports, btnLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Khởi tạo các nút bấm
        btnManageUsers = findViewById(R.id.btnManageUsers);
        btnManageBarbers=findViewById(R.id.btnManageBarbers);
        btnManageStores = findViewById(R.id.btnManageStores);
        btnManageAppointments = findViewById(R.id.btnManageAppointments);
        btnViewReports = findViewById(R.id.btnViewReports);
        btnLogOut = findViewById(R.id.btnLogOut);

        // Xử lý sự kiện cho từng nút bấm
        btnManageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Điều hướng đến màn hình quản lý người dùng
                Intent intent = new Intent(AdminActivity.this, ManageUsersActivity.class);
                startActivity(intent);
            }
        });
        btnManageBarbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Điều hướng đến màn hình quản lý người dùng
                Intent intent = new Intent(AdminActivity.this, ManageBarbersActivity.class);
                startActivity(intent);
            }
        });

        btnManageStores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Điều hướng đến màn hình quản lý cửa hàng
                Intent intent = new Intent(AdminActivity.this, ManageStoresActivity.class);
                startActivity(intent);
            }
        });

        btnManageAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Điều hướng đến màn hình quản lý lịch hẹn
                Intent intent = new Intent(AdminActivity.this, ManageAppointmentsActivity.class);
                startActivity(intent);
            }
        });

        btnViewReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Điều hướng đến màn hình xem báo cáo
                Intent intent = new Intent(AdminActivity.this, ReportsActivity.class);
                startActivity(intent);
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Điều hướng đến màn hình đăng nhập
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}

