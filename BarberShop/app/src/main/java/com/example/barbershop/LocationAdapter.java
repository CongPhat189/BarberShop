package com.example.barbershop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private List<Location> locationList;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        void onEdit(Location location);
        void onDelete(Location location);
    }

    public LocationAdapter(List<Location> locationList, OnItemClickListener listener) {
        this.locationList = locationList;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = locationList.get(position);
        holder.tvName.setText(location.getName());
        holder.tvAddress.setText(location.getAddress());
        holder.tvContact.setText(location.getContact());
        holder.tvService.setText(location.getService());
        holder.tvPrice.setText(location.getPrice());
        String imageUri = location.getImageUri();
        if (imageUri != null && !imageUri.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(Uri.parse(imageUri))
                    .into(holder.locationImage);
        }else {
            holder.locationImage.setImageResource(R.drawable.icon_shop);
        }


        holder.btnEdit.setOnClickListener(v ->
        {

                Intent intent = new Intent(context, AddEditLocationActivity.class);
                intent.putExtra("location_id", location.getId());

                context.startActivity(intent);

        });
        holder.btnDelete.setOnClickListener(v -> {
            listener.onDelete(location);

        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvService, tvContact,tvPrice;
        Button btnEdit, btnDelete;
        ImageView locationImage;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvLocationName);
            tvAddress = itemView.findViewById(R.id.tvLocationAddress);
            tvService = itemView.findViewById(R.id.tvLocationService);
            tvContact = itemView.findViewById(R.id.tvLocationContact);
            tvPrice = itemView.findViewById(R.id.tvLocationPrice);
            btnEdit = itemView.findViewById(R.id.btnEditStore);
            btnDelete = itemView.findViewById(R.id.btnDeleteStore);
            locationImage = itemView.findViewById(R.id.location_image);
        }
    }
}

