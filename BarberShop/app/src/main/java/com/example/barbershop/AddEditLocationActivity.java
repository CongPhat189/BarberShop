package com.example.barbershop;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class AddEditLocationActivity extends AppCompatActivity {

    private EditText etLocationName, etLocationAddress, etLocationService, etLocationPrice, etLocationContact;
    private Button btnSelectImage, btnSaveLocation;
    private ImageView ivLocationImage;
    private String imageUri = "";
    private Uri selectedImageUri;
    private int locationId = -1; // Default là thêm mới
    private static final int PICK_IMAGE_REQUEST = 1;
    private boolean isEditMode = false;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_location);

        // Ánh xạ View
        etLocationName = findViewById(R.id.etLocationName);
        etLocationAddress = findViewById(R.id.etLocationAddress);
        etLocationService = findViewById(R.id.etLocationService);
        etLocationPrice = findViewById(R.id.etLocationPrice);
        etLocationContact = findViewById(R.id.etLocationContact);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        ivLocationImage = findViewById(R.id.ivLocationImage);
        btnSaveLocation = findViewById(R.id.btnSaveLocation);

        dbHelper = new DatabaseHelper(this);


        // Kiểm tra chế độ sửa
        Intent intent = getIntent();
        if (intent.hasExtra("location_id")) {
            isEditMode = true;
            locationId = intent.getIntExtra("location_id", -1);
            loadLocationDetails(locationId);
        }

        // Chọn hình ảnh
        btnSelectImage.setOnClickListener(v -> selectImage());

        // Lưu địa điểm
        btnSaveLocation.setOnClickListener(v -> saveLocation());
    }

    // Tải thông tin địa điểm nếu chỉnh sửa
    private void loadLocationDetails(int id) {
        Location location = dbHelper.getLocationById(id);
        if (location != null) {
            etLocationName.setText(location.getName());
            etLocationAddress.setText(location.getAddress());
            etLocationService.setText(location.getService());
            etLocationPrice.setText(String.valueOf(location.getPrice()));
            etLocationContact.setText(location.getContact());
            imageUri = location.getImageUri();

            if (!imageUri.isEmpty()) {
                ivLocationImage.setImageURI(Uri.parse(imageUri));
            }
        }
    }

    // Chọn hình ảnh từ thiết bị
    private void selectImage() {


        // Open image picker
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                selectedImageUri = data.getData();
                String imageUriString = selectedImageUri.toString();
                ivLocationImage.setImageURI(selectedImageUri);





                try {
                    // Display the selected image
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ivLocationImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Không thể chọn ảnh", Toast.LENGTH_SHORT).show();
                }
            }
        }


    // Lưu thông tin địa điểm vào SQLite
    private void saveLocation() {
                SharedPreferences preferences = getSharedPreferences("LocationSession", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("location_id", locationId) ;
                editor.apply();
                String name = etLocationName.getText().toString().trim();
                String address = etLocationAddress.getText().toString().trim();
                String service = etLocationService.getText().toString().trim();
                String price = etLocationPrice.getText().toString().trim();
                String contact = etLocationContact.getText().toString().trim();
                String imageUri = (selectedImageUri != null) ? selectedImageUri.toString() : null;

        if (name.isEmpty() || address.isEmpty() || service.isEmpty() || price.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }



        if (locationId == -1) {
            // Thêm mới
            boolean result = dbHelper.addLocation(name, address, service, price, contact, imageUri);
            if (result) {
                Toast.makeText(this, "Thêm địa điểm thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Thêm địa điểm thất bại!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Cập nhật
            if (isEditMode) {
                boolean result = dbHelper.updateLocation(locationId,name, address, service, price, contact, imageUri);

                if (result) {
                    Toast.makeText(this, "Cập nhật địa điểm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }}
        }
    }
}

