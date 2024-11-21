package com.example.barbershop;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManageUsersActivity extends AppCompatActivity {

    private EditText etSearchUser;
    private Button btnAddUser;
    private RecyclerView rvUsers;

    private DatabaseHelper databaseHelper;
    private ArrayList<User> userList;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        etSearchUser = findViewById(R.id.etSearchUser);
        btnAddUser = findViewById(R.id.btnAddUser);
        rvUsers = findViewById(R.id.rvUsers);

        databaseHelper = new DatabaseHelper(this);

        // Load danh sách người dùng
        loadUserList();

        // Tìm kiếm người dùng
        etSearchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterUsers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // Thêm người dùng
        btnAddUser.setOnClickListener(v -> {
            Intent intent = new Intent(ManageUsersActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loadUserList() {
        userList = (ArrayList<User>) databaseHelper.getAllUsers();
        userAdapter = new UserAdapter(this, userList, databaseHelper);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.setAdapter(userAdapter);
    }

    private void filterUsers(String query) {
        ArrayList<User> filteredList = new ArrayList<>();
        for (User user : userList) {
            if (user.getUsername().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(user);
            }
        }
        userAdapter = new UserAdapter(this, filteredList, databaseHelper);
        rvUsers.setAdapter(userAdapter);
    }
}
