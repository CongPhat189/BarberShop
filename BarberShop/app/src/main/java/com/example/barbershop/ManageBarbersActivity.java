package com.example.barbershop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManageBarbersActivity extends AppCompatActivity {

    private Button btnAddBarber;
    private RecyclerView rvBarbers;

    private DatabaseHelper databaseHelper;
    private ArrayList<Barber> barberList;
    private BarberAdapter barberAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_barbers);

        btnAddBarber = findViewById(R.id.btnAddBarber);
        rvBarbers = findViewById(R.id.rvBarbers);

        databaseHelper = new DatabaseHelper(this);

        loadBarberList();

        btnAddBarber.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddBarberActivity.class);
            startActivity(intent);
        });
    }

    private void loadBarberList() {
        barberList = databaseHelper.getAllBarbers();
        barberAdapter = new BarberAdapter(this, barberList, databaseHelper);
        rvBarbers.setLayoutManager(new LinearLayoutManager(this));
        rvBarbers.setAdapter(barberAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBarberList(); // Tải lại danh sách thợ sau khi thêm mới
    }

}
