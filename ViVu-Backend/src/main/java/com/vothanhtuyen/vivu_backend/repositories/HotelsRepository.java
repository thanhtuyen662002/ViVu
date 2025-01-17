package com.vothanhtuyen.vivu_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vothanhtuyen.vivu_backend.entities.Hotels;

@Repository
public interface HotelsRepository extends JpaRepository<Hotels, Long>{
    Optional<List<Hotels>> findAllByLocationsId(Long locationId);
}
