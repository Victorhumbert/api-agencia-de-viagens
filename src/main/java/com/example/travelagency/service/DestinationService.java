package com.example.travelagency.service;

import com.example.travelagency.dto.DestinationRequest;
import com.example.travelagency.dto.ReservationRequest;
import com.example.travelagency.dto.ReservationResponse;
import com.example.travelagency.model.Destination;

import java.util.List;
import java.util.Optional;

public interface DestinationService {
    Destination create(DestinationRequest request);
    List<Destination> list(Optional<String> name, Optional<String> location);
    Destination getById(Long id);
    Destination update(Long id, DestinationRequest request);
    void delete(Long id);
    Destination addRating(Long id, int rating);
    ReservationResponse reserve(Long id, ReservationRequest request);
}
