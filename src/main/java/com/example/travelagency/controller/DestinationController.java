package com.example.travelagency.controller;

import com.example.travelagency.dto.DestinationRequest;
import com.example.travelagency.dto.ReservationRequest;
import com.example.travelagency.dto.ReservationResponse;
import com.example.travelagency.model.Destination;
import com.example.travelagency.service.DestinationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    private final DestinationService service;

    public DestinationController(DestinationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Destination create(@RequestBody DestinationRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<Destination> list(@RequestParam Optional<String> name, @RequestParam Optional<String> location) {
        return service.list(name, location);
    }

    @GetMapping("/{id}")
    public Destination getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Destination replace(@PathVariable Long id, @RequestBody DestinationRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/rating")
    public Destination addRating(@PathVariable Long id, @RequestParam int score) {
        return service.addRating(id, score);
    }

    @PostMapping("/{id}/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse reserve(@PathVariable Long id, @RequestBody ReservationRequest request) {
        return service.reserve(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
