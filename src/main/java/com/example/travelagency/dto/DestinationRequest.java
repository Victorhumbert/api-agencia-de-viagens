package com.example.travelagency.dto;

public class DestinationRequest {
    private String name;
    private String location;
    private String description;
    private Integer availablePackages;

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

    public Integer getAvailablePackages() {
        return availablePackages;
    }

    public void setAvailablePackages(Integer availablePackages) {
        this.availablePackages = availablePackages;
    }
}
