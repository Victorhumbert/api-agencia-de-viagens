package com.example.travelagency.repository;

import com.example.travelagency.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByNameContainingIgnoreCase(String name);
    List<Destination> findByLocationContainingIgnoreCase(String location);
    List<Destination> findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(String name, String location);
}
