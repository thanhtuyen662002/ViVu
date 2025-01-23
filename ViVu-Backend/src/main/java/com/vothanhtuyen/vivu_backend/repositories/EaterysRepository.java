package com.vothanhtuyen.vivu_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vothanhtuyen.vivu_backend.entities.Eatery;

@Repository
public interface EaterysRepository extends JpaRepository<Eatery, Long> {
    Optional<List<Eatery>> findAllByLocationsId(Long locationId);
}
