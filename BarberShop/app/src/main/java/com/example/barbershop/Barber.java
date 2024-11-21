package com.example.barbershop;

public class Barber {
    private int id;
    private String name;
    private int experience; // Số năm kinh nghiệm
    private float rating;   // Đánh giá của thợ

    public Barber(int id, String name, int experience, float rating) {
        this.id = id;
        this.name = name;
        this.experience = experience;
        this.rating = rating;
    }

    // Getter và Setter
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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
