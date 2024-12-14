package com.example.barbershop;



import android.app.AlertDialog;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AdminBookingAdapter extends RecyclerView.Adapter<AdminBookingAdapter.BookingViewHolder> {

    private Context context;
    private List<Booking> bookingList;

    public AdminBookingAdapter(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.tvName.setText("Tên: " + booking.getName());
        holder.tvPhone.setText("SĐT: " + booking.getPhone());
        holder.tvDate.setText("Ngày: " + booking.getDate());
        holder.tvTime.setText("Giờ: " + booking.getTime());
        holder.tvLocation.setText("Cửa hàng: " + booking.getLocationName());
        holder.tvServices.setText("Dịch vụ: " + booking.getServices());
        holder.tvPayment.setText("Thanh toán: " + booking.getPaymentMethod());
        holder.tvStatus.setText("Trạng thái: " + booking.getStatus());
        holder.tvPrice.setText("Tổng tiền: " + booking.getPrice());
        holder.btnCancelBooking.setOnClickListener(v -> {
            try {
                if (booking.getDate() == null || booking.getDate().isEmpty()) {
                    Toast.makeText(context, "Ngày hẹn không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date bookingDate = sdf.parse(booking.getDate());

                if (bookingDate == null) {
                    Toast.makeText(context, "Ngày hẹn không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo ngày hiện tại lúc 00:00
                Calendar todayCalendar = Calendar.getInstance();
                todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
                todayCalendar.set(Calendar.MINUTE, 0);
                todayCalendar.set(Calendar.SECOND, 0);
                todayCalendar.set(Calendar.MILLISECOND, 0);
                Date today = todayCalendar.getTime();

                // Tạo ngày hẹn lúc 00:00
                Calendar bookingCalendar = Calendar.getInstance();
                bookingCalendar.setTime(bookingDate);
                bookingCalendar.set(Calendar.HOUR_OF_DAY, 0);
                bookingCalendar.set(Calendar.MINUTE, 0);
                bookingCalendar.set(Calendar.SECOND, 0);
                bookingCalendar.set(Calendar.MILLISECOND, 0);
                Date normalizedBookingDate = bookingCalendar.getTime();

                // So sánh ngày
                if (normalizedBookingDate.after(today)) {
                    long diffInMillis = normalizedBookingDate.getTime() - today.getTime();
                    long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);

                    if (diffInDays >= 1) {
                        new AlertDialog.Builder(context)
                                .setTitle("Xác nhận hủy lịch")
                                .setMessage("Bạn có chắc chắn muốn hủy lịch hẹn?")
                                .setPositiveButton("Đồng ý", (dialog, which) -> {
                                    // Xóa lịch từ SQLite
                                    DatabaseHelper dbHelper = new DatabaseHelper(context);
                                    dbHelper.deleteBooking(booking.getBooking_id());

                                    // Cập nhật RecyclerView
                                    if (position >= 0 && position < bookingList.size()) {
                                        bookingList.remove(position);
                                        notifyItemRemoved(position);
                                    }
                                    Toast.makeText(context, "Đã hủy lịch hẹn", Toast.LENGTH_SHORT).show();

                                })
                                .setNegativeButton("Hủy", null)
                                .show();
                    } else {
                        Toast.makeText(context, "Bạn chỉ có thể hủy lịch trước ít nhất 1 ngày", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Lịch hẹn đã qua hoặc không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Lỗi khi xử lý thời gian hủy", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvDate, tvTime, tvLocation, tvServices, tvPayment, tvStatus,tvPrice;
        Button btnCancelBooking;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCustomerName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvServices = itemView.findViewById(R.id.tvService);
            tvPayment = itemView.findViewById(R.id.tvPayment);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvStatus = itemView.findViewById(R.id.tvBookingStatus);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnCancelBooking = itemView.findViewById(R.id.btnCancelBooking);

        }
    }
}
