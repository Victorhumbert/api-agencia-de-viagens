package com.example.travelagency.dto;

public class ReservationResponse {
    private String reservationId;
    private Long destinationId;
    private String message;

    public ReservationResponse() {}

    public ReservationResponse(String reservationId, Long destinationId, String message) {
        this.reservationId = reservationId;
        this.destinationId = destinationId;
        this.message = message;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
