package com.example.barbershop;

public class Location {
    private int id;
    private String name;
    private String address;
    private String service;
    private String price;
    private String contact;
    private String imageUri;

    // Constructor
    public Location(int id, String name, String address, String service, String price, String contact, String imageUri) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.service = service;
        this.price = price;
        this.contact = contact;
        this.imageUri = imageUri;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}

