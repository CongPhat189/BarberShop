package com.example.barbershop;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNewUsername, etNewPassword, etConfirmPassword;
    private Button btnRegisterUser;
    private DatabaseHelper db;
    private CheckBox cbIsAdmin;
    private CheckBox cbShowPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etNewUsername=findViewById(R.id.etNewUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword=findViewById(R.id.etConfirmPassword);
        cbIsAdmin = findViewById(R.id.cbIsAdmin);
        cbShowPassword=findViewById(R.id.cbShowPassword);
        btnRegisterUser = findViewById(R.id.btnRegisterUser);
        
        db = new DatabaseHelper(this);
        cbShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Hiển thị mật khẩu
                etNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                etConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                // Ẩn mật khẩu
                etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            // Di chuyển con trỏ đến cuối
            etNewPassword.setSelection(etNewPassword.length());
            etConfirmPassword.setSelection(etConfirmPassword.length());
        });

        // Xử lý sự kiện khi nhấn nút Đăng ký
        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etNewUsername.getText().toString().trim();
                String password = etNewPassword.getText().toString().trim();
                String role = cbIsAdmin.isChecked() ? "admin" : "user";

                // Kiểm tra nếu tên đăng nhập hoặc mật khẩu rỗng
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Kiểm tra người dùng đã tồn tại chưa
                    if (db.isUserExists(username)) {
                        Toast.makeText(RegisterActivity.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                    } else {
                        // Thêm người dùng vào cơ sở dữ liệu
                        boolean success = db.insertUser(username, password,role);
                        if (success) {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            // Điều hướng về màn hình đăng nhập
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });



    }
}
