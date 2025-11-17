package com.example.travelagency.model;

public class Destination {
    private Long id;
    private String name;
    private String location;
    private String description;
    private double averageRating;
    private int ratingCount;
    private int availablePackages;

    public Destination() {}

    public Destination(Long id, String name, String location, String description, int availablePackages) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.availablePackages = availablePackages;
        this.averageRating = 0.0;
        this.ratingCount = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public int getAvailablePackages() {
        return availablePackages;
    }

    public void setAvailablePackages(int availablePackages) {
        this.availablePackages = availablePackages;
    }
}
