package com.example.barbershop;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditUserActivity extends AppCompatActivity {

    private EditText etEditUsername, etEditPassword;
    private Button btnSaveChanges;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        etEditUsername = findViewById(R.id.etEditUsername);
        etEditPassword = findViewById(R.id.etEditPassword);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        databaseHelper = new DatabaseHelper(this);

        String username = getIntent().getStringExtra("username");
        etEditUsername.setText(username);

        btnSaveChanges.setOnClickListener(v -> {
            String newPassword = etEditPassword.getText().toString().trim();

            if (newPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isUpdated = databaseHelper.updateUserPassword(username, newPassword);
            if (isUpdated) {
                Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
