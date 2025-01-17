package com.vothanhtuyen.vivu_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vothanhtuyen.vivu_backend.entities.Places;

@Repository
public interface PlacesRepository extends JpaRepository<Places, Long> {
    Optional<List<Places>> findAllByLocationsId(Long locationId);
}
