package com.example.barbershop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBarberActivity extends AppCompatActivity {

    private EditText etBarberName, etExperience, etRating;
    private Button btnSaveBarber;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barber);

        // Ánh xạ các view
        etBarberName = findViewById(R.id.etBarberName);
        etExperience = findViewById(R.id.etExperience);
        etRating = findViewById(R.id.etRating);
        btnSaveBarber = findViewById(R.id.btnSaveBarber);

        databaseHelper = new DatabaseHelper(this);

        // Xử lý sự kiện lưu thợ
        btnSaveBarber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etBarberName.getText().toString().trim();
                String experienceStr = etExperience.getText().toString().trim();
                String ratingStr = etRating.getText().toString().trim();

                // Kiểm tra dữ liệu hợp lệ
                if (name.isEmpty() || experienceStr.isEmpty() || ratingStr.isEmpty()) {
                    Toast.makeText(AddBarberActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int experience = Integer.parseInt(experienceStr);
                float rating = Float.parseFloat(ratingStr);

                // Kiểm tra giới hạn của đánh giá
                if (rating < 0 || rating > 5) {
                    Toast.makeText(AddBarberActivity.this, "Đánh giá phải từ 0 đến 5!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lưu vào cơ sở dữ liệu
                boolean success = databaseHelper.addBarber(name, experience, rating);

                if (success) {
                    Toast.makeText(AddBarberActivity.this, "Thêm thợ thành công!", Toast.LENGTH_SHORT).show();
                    finish(); // Đóng activity và quay lại danh sách
                } else {
                    Toast.makeText(AddBarberActivity.this, "Thêm thợ thất bại. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
