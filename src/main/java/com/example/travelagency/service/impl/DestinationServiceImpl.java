package com.example.travelagency.service.impl;

import com.example.travelagency.dto.DestinationRequest;
import com.example.travelagency.dto.ReservationRequest;
import com.example.travelagency.dto.ReservationResponse;
import com.example.travelagency.model.Destination;
import com.example.travelagency.service.DestinationService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.http.HttpStatus.*;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final Map<Long, Destination> storage = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    @Override
    public Destination create(DestinationRequest request) {
        long id = idCounter.incrementAndGet();
        Destination d = new Destination(id,
                request.getName(),
                request.getLocation(),
                request.getDescription(),
                request.getAvailablePackages() != null ? request.getAvailablePackages() : 0);
        storage.put(id, d);
        return d;
    }

    @Override
    public List<Destination> list(Optional<String> name, Optional<String> location) {
        List<Destination> result = new ArrayList<>();
        for (Destination d : storage.values()) {
            boolean matches = true;
            if (name.isPresent()) {
                matches &= d.getName() != null && d.getName().toLowerCase().contains(name.get().toLowerCase());
            }
            if (location.isPresent()) {
                matches &= d.getLocation() != null && d.getLocation().toLowerCase().contains(location.get().toLowerCase());
            }
            if (matches) result.add(d);
        }
        return result;
    }

    @Override
    public Destination getById(Long id) {
        Destination d = storage.get(id);
        if (d == null) throw new ResponseStatusException(NOT_FOUND, "Destination not found");
        return d;
    }

    @Override
    public Destination update(Long id, DestinationRequest request) {
        Destination existing = getById(id);
        if (request.getName() != null) existing.setName(request.getName());
        if (request.getLocation() != null) existing.setLocation(request.getLocation());
        if (request.getDescription() != null) existing.setDescription(request.getDescription());
        if (request.getAvailablePackages() != null) existing.setAvailablePackages(request.getAvailablePackages());
        storage.put(id, existing);
        return existing;
    }

    @Override
    public void delete(Long id) {
        Destination removed = storage.remove(id);
        if (removed == null) throw new ResponseStatusException(NOT_FOUND, "Destination not found");
    }

    @Override
    public Destination addRating(Long id, int rating) {
        if (rating < 1 || rating > 10) throw new ResponseStatusException(BAD_REQUEST, "Rating must be between 1 and 10");
        Destination d = getById(id);
        synchronized (d) {
            double total = d.getAverageRating() * d.getRatingCount();
            total += rating;
            int newCount = d.getRatingCount() + 1;
            d.setRatingCount(newCount);
            d.setAverageRating(total / newCount);
        }
        storage.put(id, d);
        return d;
    }

    @Override
    public ReservationResponse reserve(Long id, ReservationRequest request) {
        Destination d = getById(id);
        synchronized (d) {
            int qty = Math.max(1, request.getQuantity());
            if (d.getAvailablePackages() < qty) {
                throw new ResponseStatusException(BAD_REQUEST, "Not enough packages available");
            }
            d.setAvailablePackages(d.getAvailablePackages() - qty);
        }
        storage.put(id, d);
        String reservationId = UUID.randomUUID().toString();
        String msg = "Reserved " + request.getQuantity() + " package(s) for destination " + id;
        return new ReservationResponse(reservationId, id, msg);
    }
}
