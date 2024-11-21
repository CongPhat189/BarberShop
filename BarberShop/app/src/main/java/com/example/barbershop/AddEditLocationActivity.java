package com.example.barbershop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditLocationActivity extends AppCompatActivity {

    private EditText etLocationName, etLocationAddress, etLocationService, etLocationPrice, etLocationContact;
    private Button btnSelectImage, btnSaveLocation;
    private ImageView ivLocationImage;
    private String imageUri = "";
    private int locationId = -1; // Default là thêm mới

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

        // Kiểm tra nếu là chỉnh sửa
        Intent intent = getIntent();
        if (intent.hasExtra("locationId")) {
            locationId = intent.getIntExtra("locationId", -1);
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
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imageUri = selectedImage.toString();
            ivLocationImage.setImageURI(selectedImage);
        }
    }

    // Lưu thông tin địa điểm vào SQLite
    private void saveLocation() {
        String name = etLocationName.getText().toString();
        String address = etLocationAddress.getText().toString();
        String service = etLocationService.getText().toString();
        String priceStr = etLocationPrice.getText().toString();
        String contact = etLocationContact.getText().toString();

        if (name.isEmpty() || address.isEmpty() || service.isEmpty() || priceStr.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);

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
            boolean result = dbHelper.updateLocation(locationId, name, address, service, price, contact, imageUri);
            if (result) {
                Toast.makeText(this, "Cập nhật địa điểm thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Cập nhật địa điểm thất bại!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

