package com.vothanhtuyen.vivu_backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vothanhtuyen.vivu_backend.entities.Locations;

@Repository
public interface LocationsRepository extends JpaRepository<Locations, Integer> {
    Optional<Locations> findByNameNormalized(String nameNormalized);

    Optional<Locations> findById(Long id);
}
