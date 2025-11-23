package com.example.travelagency.service.impl;

import com.example.travelagency.dto.DestinationRequest;
import com.example.travelagency.dto.ReservationRequest;
import com.example.travelagency.dto.ReservationResponse;
import com.example.travelagency.model.Destination;
import com.example.travelagency.repository.DestinationRepository;
import com.example.travelagency.service.DestinationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@Service
@Transactional
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository repository;

    public DestinationServiceImpl(DestinationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Destination create(DestinationRequest request) {
        Destination d = new Destination();
        d.setName(request.getName());
        d.setLocation(request.getLocation());
        d.setDescription(request.getDescription());
        d.setAvailablePackages(request.getAvailablePackages() != null ? request.getAvailablePackages() : 0);
        d.setAverageRating(0.0);
        d.setRatingCount(0);
        return repository.save(d);
    }

    @Override
    public List<Destination> list(Optional<String> name, Optional<String> location) {
        if (name.isPresent() && location.isPresent()) {
            return repository.findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(name.get(), location.get());
        } else if (name.isPresent()) {
            return repository.findByNameContainingIgnoreCase(name.get());
        } else if (location.isPresent()) {
            return repository.findByLocationContainingIgnoreCase(location.get());
        }
        return repository.findAll();
    }

    @Override
    public Destination getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Destination not found"));
    }

    @Override
    public Destination update(Long id, DestinationRequest request) {
        Destination existing = getById(id);
        if (request.getName() != null) existing.setName(request.getName());
        if (request.getLocation() != null) existing.setLocation(request.getLocation());
        if (request.getDescription() != null) existing.setDescription(request.getDescription());
        if (request.getAvailablePackages() != null) existing.setAvailablePackages(request.getAvailablePackages());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResponseStatusException(NOT_FOUND, "Destination not found");
        repository.deleteById(id);
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
        return repository.save(d);
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
        repository.save(d);
        String reservationId = UUID.randomUUID().toString();
        String msg = "Reserved " + request.getQuantity() + " package(s) for destination " + id;
        return new ReservationResponse(reservationId, id, msg);
    }
}
