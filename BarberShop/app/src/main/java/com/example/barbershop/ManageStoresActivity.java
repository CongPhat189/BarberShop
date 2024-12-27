package com.example.barbershop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ManageStoresActivity extends AppCompatActivity {

    private RecyclerView rvLocations;
    private LocationAdapter adapter;
    private List<Location> locationList;
    private DatabaseHelper dbHelper;
    private Button btnAddLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_stores);

        rvLocations = findViewById(R.id.rvLocations);
         btnAddLocation   = findViewById(R.id.btnAddLocation);

        dbHelper = new DatabaseHelper(this);
        locationList = dbHelper.getAllLocations();
        adapter = new LocationAdapter(locationList, new LocationAdapter.OnItemClickListener() {


            @Override
            public void onEdit(Location location) {
                Intent intent = new Intent(ManageStoresActivity.this, AddEditLocationActivity.class);
                startActivity(intent);
                dbHelper.updateLocation( location.getId(),location.getName(), location.getAddress(), location.getService(), location.getPrice(), location.getContact(), location.getImageUri());

            }

            @Override
            public void onDelete(Location location) {
                dbHelper.deleteLocation(location.getId());
                locationList.remove(location);
                adapter.notifyDataSetChanged();
            }
        });


        rvLocations.setLayoutManager(new LinearLayoutManager(this));
        rvLocations.setAdapter(adapter);

        btnAddLocation.setOnClickListener(v -> {
            Intent intent = new Intent(ManageStoresActivity.this, AddEditLocationActivity.class);
            startActivity(intent);
        });

        rvLocations.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationList.clear();
        locationList.addAll(dbHelper.getAllLocations());
        adapter.notifyDataSetChanged();
    }



}

