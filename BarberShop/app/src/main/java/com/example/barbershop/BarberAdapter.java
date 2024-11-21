package com.example.barbershop;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BarberAdapter extends RecyclerView.Adapter<BarberAdapter.BarberViewHolder> {

    private Context context;
    private ArrayList<Barber> barberList;
    private DatabaseHelper databaseHelper;

    public BarberAdapter(Context context, ArrayList<Barber> barberList, DatabaseHelper databaseHelper) {
        this.context = context;
        this.barberList = barberList;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public BarberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_barber, parent, false);
        return new BarberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarberViewHolder holder, int position) {
        Barber barber = barberList.get(position);
        holder.tvName.setText(barber.getName());
        holder.tvExperience.setText("Kinh nghiệm: " + barber.getExperience() + " năm");
        holder.tvRating.setText("Đánh giá: " + barber.getRating() + " sao");

        holder.btnDelete.setOnClickListener(v -> showDeleteDialog(barber.getId(), position));
    }

    @Override
    public int getItemCount() {
        return barberList.size();
    }

    private void showDeleteDialog(int barberId, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa Thợ");
        builder.setMessage("Bạn có chắc chắn muốn xóa thợ này?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            databaseHelper.deleteBarber(barberId);
            barberList.remove(position);
            notifyItemRemoved(position);
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    public static class BarberViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvExperience, tvRating;
        Button btnDelete;

        public BarberViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvBarberName);
            tvExperience = itemView.findViewById(R.id.tvExperience);
            tvRating = itemView.findViewById(R.id.tvRating);
            btnDelete = itemView.findViewById(R.id.btnDeleteBarber);
        }
    }
}

