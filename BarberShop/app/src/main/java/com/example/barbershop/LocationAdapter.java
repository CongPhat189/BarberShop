package com.example.barbershop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private List<Location> locationList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEdit(Location location);
        void onDelete(Location location);
    }

    public LocationAdapter(List<Location> locationList, OnItemClickListener listener) {
        this.locationList = locationList;
        this.listener = listener;
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
        holder.tvService.setText(location.getService());

        holder.itemView.setOnClickListener(v -> listener.onEdit(location));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onDelete(location);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvService;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvLocationName);
            tvAddress = itemView.findViewById(R.id.tvLocationAddress);
            tvService = itemView.findViewById(R.id.tvLocationService);
        }
    }
}

