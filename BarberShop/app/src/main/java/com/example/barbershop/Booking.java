package com.example.barbershop;




public class Booking {
    private int booking_id;
    private int user_id;
    private String full_name;
    private String phone;
    private String date;
    private String time;
    private String locationName;
    private String services;
    private Double price;
    private String paymentMethod;
    private String status;


    public Booking(int booking_id, int user_id,String full_name, String phone, String date,String time, String locationName, String services,Double price, String paymentMethod,String status) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.full_name = full_name;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.locationName = locationName;
        this.services = services;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }



    public int getBooking_id() { return booking_id; }
    public int getUser_id() { return user_id; }
    public String getName() { return full_name; }
    public String getPhone() { return phone; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getLocationName() { return locationName; }
    public String getServices() { return services; }
    public Double getPrice() { return price; }
    public String getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }
}
