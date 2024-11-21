package com.example.barbershop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private ArrayList<User> userList;
    private DatabaseHelper databaseHelper;

    public UserAdapter(Context context, ArrayList<User> userList, DatabaseHelper databaseHelper) {
        this.context = context;
        this.userList = userList;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvUsername.setText(user.getUsername());
        holder.tvRole.setText(user.getRole());

        // Xử lý nút Xóa
        holder.btnDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog(user.getUsername(), position);
        });

        // Xử lý nút Sửa
        holder.btnEdit.setOnClickListener(v -> {
            // Chuyển sang EditUserActivity
            Intent intent = new Intent(context, EditUserActivity.class);
            intent.putExtra("username", user.getUsername());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private void showDeleteConfirmationDialog(String username, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa người dùng " + username + " không?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            boolean isDeleted = databaseHelper.deleteUser(username);
            if (isDeleted) {
                userList.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Xóa người dùng thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Xóa người dùng thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView tvUsername, tvRole;
        Button btnEdit, btnDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvRole = itemView.findViewById(R.id.tvRole);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
