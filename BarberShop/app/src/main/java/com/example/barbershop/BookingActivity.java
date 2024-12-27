package com.example.barbershop;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

public class BookingActivity extends AppCompatActivity {

    private EditText etCustomerName, etPhone;
    private TextView tvSelectedDate, tvSelectedTime, tvPrice;
    private Spinner spinnerLocations;
    private RadioGroup rgPaymentMethod;

    private Button btnBookAppointment, btnManageAppointments, btnLogOut;
    private DatabaseHelper dbHelper;
    private Spinner spinnerService;
    private TextView tvStatus;
    private float[] servicePrices = {100000, 50000, 300000, 200000 , 500000, 550000, 600000};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Ánh xạ
        etCustomerName = findViewById(R.id.etCustomerName);
        etPhone = findViewById(R.id.etPhone);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvSelectedTime = findViewById(R.id.tvSelectedTime);
        spinnerLocations = findViewById(R.id.spinnerLocations);
        rgPaymentMethod = findViewById(R.id.rgPaymentMethod);
        spinnerService = findViewById(R.id.spinnerService);
        btnBookAppointment = findViewById(R.id.btnBookAppointment);
        tvStatus = findViewById(R.id.tvBookingStatus);
        tvPrice = findViewById(R.id.tvPrice);
        btnManageAppointments = findViewById(R.id.btnManageAppointments);
        btnLogOut = findViewById(R.id.btnLogOut);

        dbHelper = new DatabaseHelper(this);
        // Lấy danh sách cửa hàng
        List<String> locations = dbHelper.getAllLocationsForSpinner();

        // Gán dữ liệu vào Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocations.setAdapter(adapter);

        spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvPrice.setText("Giá: " + servicePrices[position] + " VND");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Danh sách cửa hàng
        spinnerLocations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLocation = parent.getItemAtPosition(position).toString();
                Toast.makeText(BookingActivity.this, "Đã chọn: " + selectedLocation, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không chọn gì
            }
        });


        // Chọn ngày
        findViewById(R.id.btnSelectDate).setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePicker = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                tvSelectedDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });

        // Chọn giờ
        findViewById(R.id.btnSelectTime).setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog timePicker = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                tvSelectedTime.setText(hourOfDay + ":" + minute);
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePicker.show();
        });

        // Xử lý đặt lịch
        btnBookAppointment.setOnClickListener(v -> submitBooking());
        btnManageAppointments.setOnClickListener(v -> {
            Intent intent = new Intent(BookingActivity.this, BookingListActivity.class);
            startActivity(intent);
        });
        btnLogOut.setOnClickListener(v -> {
            Intent intent = new Intent(BookingActivity.this, LoginActivity.class);
            startActivity(intent);
        });


    }

    private void submitBooking() {
        String full_name = etCustomerName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String date = tvSelectedDate.getText().toString().trim();
        String time = tvSelectedTime.getText().toString().trim();
        String services = spinnerService.getSelectedItem().toString();
        SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int user_id = preferences.getInt("user_id", -1);
        Double price = (double) servicePrices[spinnerService.getSelectedItemPosition()];
        String status = "";



        String locationAddress = spinnerLocations.getSelectedItem().toString();
        tvStatus= findViewById(R.id.tvBookingStatus);

        int selectedPaymentId = rgPaymentMethod.getCheckedRadioButtonId();
        String paymentMethod = selectedPaymentId == R.id.rbCash ? "Tiền mặt" : "Chuyển khoản";
        if(paymentMethod=="Chuyển khoản")
            status = "Đã thanh toán";
        else
            status = "Chưa thanh toán";

        if (full_name.isEmpty() || phone.isEmpty() || date.isEmpty() || time.isEmpty() || services.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }


        boolean success = dbHelper.addBooking(user_id,full_name, phone, date, time, services, price, locationAddress, paymentMethod,status);
        if (success) {
            tvStatus.setText("Đặt lịch thành công!");

        } else {
            tvStatus.setText("Đặt lịch thất bại!");
        }

    }
}

